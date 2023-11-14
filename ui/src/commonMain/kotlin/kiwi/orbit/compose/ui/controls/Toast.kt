package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.utils.durationScale
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

public interface ToastData {
    public val message: String
    public val icon: @Composable (() -> Painter)?

    public val animationDuration: StateFlow<Duration?>

    public suspend fun run(accessibilityManager: AccessibilityManager?)

    public fun pause()

    public fun resume()

    public fun dismiss()

    public fun dismissed()
}

@Composable
public fun Toast(
    toastData: ToastData,
) {
    val animateDuration by toastData.animationDuration.collectAsState()
    key(toastData) {
        Toast(
            message = toastData.message,
            icon = toastData.icon,
            animateDuration = animateDuration,
            onPause = toastData::pause,
            onResume = toastData::resume,
            onDismissed = toastData::dismissed,
        )
    }
}

@Composable
private fun Toast(
    message: String,
    icon: @Composable (() -> Painter)?,
    animateDuration: Duration? = Duration.ZERO,
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
    onDismissed: () -> Unit = {},
) {
    val shape = OrbitTheme.shapes.normal
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(max = 520.dp)
            .fillMaxWidth()
            .toastGesturesDetector(onPause, onResume, onDismissed),
        color = OrbitTheme.colors.content.normal,
        shape = shape,
        elevation = OrbitTheme.elevations.Level2,
    ) {
        val progress = remember { Animatable(0f) }
        LaunchedEffect(animateDuration) {
            // Do not run animation when animations are turned off.
            if (coroutineContext.durationScale == 0f) return@LaunchedEffect

            if (animateDuration == null) {
                progress.stop()
            } else {
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = animateDuration.toInt(DurationUnit.MILLISECONDS),
                        easing = EaseOut,
                    ),
                )
            }
        }

        val color = LocalContentColor.current
        Row(
            Modifier
                .drawBehind {
                    val fraction = progress.value * size.width
                    drawRoundRect(
                        color = color,
                        size = Size(width = fraction, height = size.height),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha = 0.1f,
                    )
                }
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                if (icon != null) {
                    Icon(
                        icon(),
                        contentDescription = null,
                    )
                }
                Text(message)
            }
        }
    }
}

private fun Modifier.toastGesturesDetector(
    onPause: () -> Unit,
    onResume: () -> Unit,
    onDismissed: () -> Unit,
): Modifier = composed {
    val offsetY = remember { Animatable(0f) }
    val alpha = remember { Animatable(1f) }

    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)
        coroutineScope {
            while (true) {
                awaitPointerEventScope {
                    // Detect a touch down event.
                    val down = awaitFirstDown()
                    onPause()
                    val pointerId = down.id

                    val velocityTracker = VelocityTracker()
                    // Stop any ongoing animation.
                    launch(start = CoroutineStart.UNDISPATCHED) {
                        offsetY.stop()
                        alpha.stop()
                    }

                    verticalDrag(pointerId) { change ->
                        onPause()
                        // Update the animation value with touch events.
                        val changeY = (offsetY.value + change.positionChange().y).coerceAtMost(0f)
                        launch {
                            offsetY.snapTo(changeY)
                        }
                        if (changeY == 0f) {
                            velocityTracker.resetTracking()
                        } else {
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position,
                            )
                        }
                    }

                    onResume()
                    // No longer receiving touch events. Prepare the animation.
                    val velocity = velocityTracker.calculateVelocity().y
                    val targetOffsetY = decay.calculateTargetValue(
                        offsetY.value,
                        velocity,
                    )
                    // The animation stops when it reaches the bounds.
                    offsetY.updateBounds(
                        lowerBound = -size.height.toFloat() * 3,
                        upperBound = size.height.toFloat(),
                    )
                    launch {
                        if (velocity >= 0 || targetOffsetY.absoluteValue <= size.height) {
                            // Not enough velocity; Slide back.
                            offsetY.animateTo(
                                targetValue = 0f,
                                initialVelocity = velocity,
                            )
                        } else {
                            // The element was swiped away.
                            launch { offsetY.animateDecay(velocity, decay) }
                            launch {
                                alpha.animateTo(targetValue = 0f, animationSpec = tween(300))
                                onDismissed()
                            }
                        }
                    }
                }
            }
        }
    }
        .offset {
            IntOffset(0, offsetY.value.roundToInt())
        }
        .alpha(alpha.value)
}

@OrbitPreviews
@Composable
internal fun ToastPreview() {
    Preview {
        Toast(
            message = "Message",
            icon = null,
        )
        Toast(
            message = "Message with icon",
            icon = { Icons.CheckCircle },
        )
        Toast(
            message = "Message with icon and very long message with many words.",
            icon = { Icons.CheckCircle },
        )
    }
}
