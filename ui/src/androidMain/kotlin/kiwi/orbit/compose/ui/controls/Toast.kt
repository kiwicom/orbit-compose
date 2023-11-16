package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kiwi.orbit.compose.icons.IconName
import kiwi.orbit.compose.icons.painter
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.utils.durationScale
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

public interface ToastData {
    public val message: String
    public val iconName: IconName?
    public val imageUrl: String?
    public val actionLabel: String?
    public val toastId: String

    public val animationDuration: StateFlow<Duration?>

    public suspend fun run(accessibilityManager: AccessibilityManager?)

    public fun performAction()

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
            animateDuration = animateDuration,
            onPause = toastData::pause,
            onResume = toastData::resume,
            onDismissed = toastData::dismissed,
        ) {
            ToastContent(
                message = toastData.message,
                iconName = toastData.iconName,
                imageUrl = toastData.imageUrl,
                actionLabel = toastData.actionLabel,
                onActionClick = toastData::performAction,
                onPause = toastData::pause,
                onResume = toastData::resume,
            )
        }
    }
}

@Composable
private fun Toast(
    animateDuration: Duration? = Duration.ZERO,
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
    onDismissed: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
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
                        durationMillis = animateDuration.inWholeMilliseconds.toInt(),
                        easing = EaseOut,
                    ),
                )
            }
        }

        val color = LocalContentColor.current
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .drawBehind {
                    val fraction = progress.value * size.width
                    drawRoundRect(
                        color = color,
                        size = Size(width = fraction, height = size.height),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha = 0.1f,
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                content()
            }
        }
    }
}

@Composable
private fun RowScope.ToastContent(
    message: String,
    iconName: IconName? = null,
    imageUrl: String? = null,
    actionLabel: String? = null,
    onActionClick: () -> Unit = {},
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
) {
    when {
        iconName != null -> Icon(
            painter = iconName.painter(),
            contentDescription = null,
            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
        )
        imageUrl != null -> AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 6.dp, top = 6.dp, bottom = 6.dp, end = 12.dp)
                .size(52.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(LocalColors.current.content.subtle),
            contentScale = ContentScale.Crop,
        )
        else -> Spacer(Modifier.width(12.dp))
    }
    Text(
        text = message,
        modifier = Modifier
            .padding(vertical = 12.dp)
            .weight(1f),
    )
    if (actionLabel != null) {
        Text(
            text = actionLabel,
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp))
                .clickable(
                    role = Role.Button,
                    onClick = onActionClick,
                )
                .toastActionGesturesDetector(onPause, onResume)
                .padding(horizontal = 12.dp)
                .wrapContentHeight(),
            fontWeight = FontWeight.Medium,
        )
    } else {
        Spacer(Modifier.width(12.dp))
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

private fun Modifier.toastActionGesturesDetector(
    onPause: () -> Unit,
    onResume: () -> Unit,
): Modifier = pointerInput(Unit) {
    while (true) {
        awaitPointerEventScope {
            awaitFirstDown()
            onPause()
            waitForUpOrCancellation()
            onResume()
        }
    }
}

@OrbitPreviews
@Composable
internal fun ToastPreview() {
    Preview {
        Toast {
            ToastContent("Message")
        }
        Toast {
            ToastContent(
                message = "Message with icon",
                iconName = IconName.CheckCircle,
            )
        }
        Toast {
            ToastContent(
                message = "Message with icon and very long message with many words.",
                iconName = IconName.CheckCircle,
            )
        }
    }
}
