package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kotlin.math.absoluteValue

@Composable
public fun InlineLoading(
    modifier: Modifier = Modifier,
    circleSize: Dp = 8.dp,
    circleDistance: Dp = 6.dp,
) {
    Row(
        modifier = modifier.height(44.dp),
        horizontalArrangement = Arrangement.spacedBy(circleDistance),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Circle(0, circleSize)
        Circle(1, circleSize)
        Circle(2, circleSize)
    }
}

@Composable
private fun Circle(
    index: Int,
    circleSize: Dp,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedValue by infiniteTransition.animateValue(
        initialValue = 0f,
        targetValue = -3f,
        typeConverter = TwoWayConverter(
            convertToVector = { value -> AnimationVector1D(value) },
            convertFromVector = { vector -> vector.value },
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = AnimationConstants.DefaultDurationMillis,
                easing = EaseInOut,
                delayMillis = 400,
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset((index + 1) * 120),
        ),
    )
    val color = OrbitTheme.colors.surface.strong

    Canvas(
        modifier = Modifier.size(circleSize),
    ) {
        drawCircle(
            color = color,
            alpha = 0.8f + 0.2f * animatedValue.absoluteValue / 3,
            center = Offset(
                x = size.center.x,
                y = size.center.y + animatedValue.dp.toPx(),
            ),
        )
    }
}

@OrbitPreviews
@Composable
internal fun InlineLoadingPreview() {
    Preview {
        InlineLoading()
    }
}
