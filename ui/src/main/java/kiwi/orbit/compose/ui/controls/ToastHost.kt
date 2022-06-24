package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import kiwi.orbit.compose.ui.utils.durationScale
import kotlin.coroutines.resume
import kotlin.math.roundToLong
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Stable
public class ToastHostState {
    private val mutex = Mutex()

    public var currentData: ToastData? by mutableStateOf(null)
        private set

    public suspend fun showToast(
        message: String,
        icon: @Composable (() -> Painter)?,
    ): Unit = mutex.withLock {
        try {
            return suspendCancellableCoroutine { cont ->
                currentData = ToastDataImpl(
                    message,
                    icon,
                    cont,
                )
            }
        } finally {
            currentData = null
        }
    }

    @Stable
    private class ToastDataImpl(
        override val message: String,
        override val icon: @Composable (() -> Painter)?,
        private val continuation: CancellableContinuation<Unit>,
    ) : ToastData {
        private var elapsed = 0L
        private var started = 0L
        private var duration = 0L
        private val _state = MutableStateFlow<Int?>(null)
        override val animationDuration: StateFlow<Int?> = _state.asStateFlow()

        override suspend fun run(accessibilityManager: AccessibilityManager?) {
            duration = durationTimeout(
                hasIcon = icon != null,
                accessibilityManager = accessibilityManager,
            )

            // Accessibility decided to show forever
            // Let's await explicit dismiss, do not run animation.
            if (duration == Long.MAX_VALUE) {
                delay(duration)
                return
            }

            resume()
            supervisorScope {
                launch {
                    animationDuration.collectLatest { duration ->
                        val animationScale = coroutineContext.durationScale
                        // Do not run animation when animations are turned off.
                        if (duration != null && animationScale != 0f) {
                            started = System.currentTimeMillis()
                            val finalDuration = (duration.toLong() * animationScale).roundToLong()
                            delay(finalDuration)
                            this@launch.cancel()
                        } else {
                            elapsed += System.currentTimeMillis() - started
                            delay(Long.MAX_VALUE)
                        }
                    }
                }
            }
        }

        override fun pause() {
            _state.value = null
        }

        override fun resume() {
            val remains = (duration - elapsed).toInt()
            if (remains > 0) {
                _state.value = remains
            } else {
                dismiss()
            }
        }

        override fun dismiss() {
            _state.value = 0
        }

        override fun dismissed() {
            if (continuation.isActive) {
                continuation.resume(Unit)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun ToastHost(
    hostState: ToastHostState,
    modifier: Modifier = Modifier,
    toast: @Composable (ToastData) -> Unit = { Toast(it) },
) {
    val accessibilityManager = LocalAccessibilityManager.current
    val currentData = hostState.currentData ?: return

    key(currentData) {
        var state by remember { mutableStateOf(false) }
        val transition = updateTransition(targetState = state, label = "toast")

        LaunchedEffect(Unit) {
            state = true
            currentData.run(accessibilityManager)
            state = false
        }

        transition.AnimatedVisibility(
            visible = { it },
            modifier = modifier,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
        ) {
            toast(currentData)
        }

        // Await dismiss animation and dismiss the Toast completely.
        // This animation workaround instead of nulling the toast data is to prevent
        // relaunching another Toast when the dismiss animation has not completed yet.
        LaunchedEffect(state, transition.currentState, transition.isRunning) {
            if (!state && !transition.currentState && !transition.isRunning) {
                currentData.dismissed()
            }
        }
    }
}

internal fun durationTimeout(
    hasIcon: Boolean,
    accessibilityManager: AccessibilityManager?,
): Long {
    val timeout = 5000L
    if (accessibilityManager == null) return timeout
    return accessibilityManager.calculateRecommendedTimeoutMillis(
        originalTimeoutMillis = timeout,
        containsIcons = hasIcon,
        containsText = true,
        containsControls = false,
    )
}
