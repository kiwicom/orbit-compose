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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import kiwi.orbit.compose.icons.IconName
import kiwi.orbit.compose.ui.utils.durationScale
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource
import kotlin.time.TimeSource.Monotonic.ValueTimeMark
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Composable
public fun rememberToastHostState(
    onActionClick: (String) -> Unit = {},
    onDismiss: (String) -> Unit = {},
): ToastHostState = rememberSaveable(
    saver = ToastHostState.Saver(onActionClick, onDismiss),
) {
    ToastHostState(onActionClick, onDismiss)
}

@Stable
public class ToastHostState(
    internal val onActionClick: (String) -> Unit,
    internal val onDismiss: (String) -> Unit,
) {
    internal companion object {
        fun Saver(
            onActionClick: (String) -> Unit,
            onDismiss: (String) -> Unit,
        ): Saver<ToastHostState, *> = listSaver(
            save = {
                it.toastDataQueue.flatMap { data ->
                    listOf(
                        data.message,
                        data.iconName,
                        data.imageUrl,
                        data.actionLabel,
                        data.toastId,
                    )
                }
            },
            restore = {
                ToastHostState(onActionClick, onDismiss).apply {
                    toastDataQueue.addAll(
                        it.chunked(5) { data ->
                            createToastData(
                                message = data[0] as String,
                                iconName = data[1] as IconName?,
                                imageUrl = data[2] as String?,
                                actionLabel = data[3] as String?,
                                toastId = data[4] as String,
                            )
                        },
                    )
                    currentToastData = toastDataQueue.firstOrNull()
                }
            },
        )
    }

    internal var currentToastData: ToastData? by mutableStateOf(null)
        private set

    private val toastDataQueue: MutableList<ToastData> = mutableListOf()

    public fun showToast(
        message: String,
        iconName: IconName? = null,
        actionLabel: String? = null,
        toastId: String = "toast",
    ) {
        showToast(message, iconName, imageUrl = null, actionLabel, toastId)
    }

    public fun showToast(
        message: String,
        imageUrl: String,
        actionLabel: String? = null,
        toastId: String = "toast",
    ) {
        showToast(message, iconName = null, imageUrl, actionLabel, toastId)
    }

    private fun showToast(
        message: String,
        iconName: IconName?,
        imageUrl: String?,
        actionLabel: String?,
        toastId: String,
    ) {
        synchronized(this) {
            toastDataQueue.add(
                createToastData(message, iconName, imageUrl, actionLabel, toastId),
            )
            if (currentToastData == null) {
                currentToastData = toastDataQueue.firstOrNull()
            }
        }
    }

    private fun createToastData(
        message: String,
        iconName: IconName?,
        imageUrl: String?,
        actionLabel: String?,
        toastId: String,
    ): ToastData = ToastDataImpl(
        message = message,
        iconName = iconName,
        imageUrl = imageUrl,
        actionLabel = actionLabel,
        toastId = toastId,
        onActionClick = { onActionClick(it.toastId) },
        onDismiss = {
            synchronized(this) {
                currentToastData = null
                toastDataQueue.remove(it)
                onDismiss(it.toastId)
                currentToastData = toastDataQueue.firstOrNull()
            }
        },
    )

    @Stable
    private class ToastDataImpl(
        override val message: String,
        override val iconName: IconName?,
        override val imageUrl: String?,
        override val actionLabel: String?,
        override val toastId: String,
        private val onActionClick: (ToastData) -> Unit,
        private val onDismiss: (ToastData) -> Unit,
    ) : ToastData {
        private var elapsed: Duration = Duration.ZERO
        private var started: ValueTimeMark? = null
        private var duration: Duration = Duration.ZERO

        private val _state = MutableStateFlow<Duration?>(null)
        override val animationDuration: StateFlow<Duration?> = _state.asStateFlow()

        override suspend fun run(accessibilityManager: AccessibilityManager?) {
            duration = durationTimeout(
                hasIcon = iconName != null,
                accessibilityManager = accessibilityManager,
            )

            // Accessibility decided to show forever
            // Let's await explicit dismiss, do not run animation.
            if (duration == Duration.INFINITE) {
                delay(duration)
                return
            }

            resume()
            supervisorScope {
                launch {
                    animationDuration.collectLatest { duration ->
                        val animationScale = coroutineContext.durationScale
                        if (duration != null) {
                            started = TimeSource.Monotonic.markNow()
                            // When animations are turned off, simply show, wait and hide.
                            val finalDuration = when (animationScale) {
                                0f -> duration.inWholeMilliseconds
                                else -> (duration.inWholeMilliseconds * animationScale).roundToLong()
                            }
                            delay(finalDuration)
                            this@launch.cancel()
                        } else {
                            elapsed += started?.elapsedNow() ?: Duration.ZERO
                            delay(Long.MAX_VALUE)
                        }
                    }
                }
            }
        }

        override fun performAction() {
            onActionClick(this)
            dismiss()
        }

        override fun pause() {
            _state.value = null
        }

        override fun resume() {
            val remains = duration - elapsed
            if (remains.isPositive()) {
                _state.value = remains
            } else {
                dismiss()
            }
        }

        override fun dismiss() {
            _state.value = Duration.ZERO
        }

        override fun dismissed() {
            onDismiss(this)
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
    val currentData = hostState.currentToastData ?: return

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
): Duration {
    val timeout = 5.seconds
    if (accessibilityManager == null) return timeout
    val millis = accessibilityManager.calculateRecommendedTimeoutMillis(
        originalTimeoutMillis = timeout.inWholeMilliseconds,
        containsIcons = hasIcon,
        containsText = true,
        containsControls = false,
    )
    return if (millis == Long.MAX_VALUE) Duration.INFINITE else millis.milliseconds
}
