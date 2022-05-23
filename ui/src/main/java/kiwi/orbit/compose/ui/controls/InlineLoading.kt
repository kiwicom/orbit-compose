package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
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
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kotlin.math.absoluteValue

@Composable
public fun InlineLoading(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(44.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Circle(0)
        Circle(1)
        Circle(2)
    }
}

@Composable
private fun Circle(
    index: Int,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedValue by infiniteTransition.animateValue(
        initialValue = 0f,
        targetValue = -3f,
        typeConverter = TwoWayConverter(
            convertToVector = { value -> AnimationVector1D(value) },
            convertFromVector = { vector -> vector.value }
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = AnimationConstants.DefaultDurationMillis,
                easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1f),
                delayMillis = 400,
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset((index + 1) * 120),
        )
    )
    val color = OrbitTheme.colors.surface.disabled

    Canvas(
        modifier = Modifier.size(8.dp),
    ) {
        drawCircle(
            color = color,
            alpha = 0.2f + 0.8f * animatedValue.absoluteValue / 3,
            center = Offset(
                x = size.center.x,
                y = size.center.y + animatedValue.dp.toPx(),
            )
        )
    }
}

@Composable
internal fun InlineLoadingPreview() {
    Preview {
        InlineLoading()
    }
}
