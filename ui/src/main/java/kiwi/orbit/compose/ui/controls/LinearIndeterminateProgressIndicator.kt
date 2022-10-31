package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Linear indeterminate progress indicator.
 *
 * Renders [LinearProgressIndicatorDefaultHeight] tall rounded line in maximum available width.
 * To change the height or width, pass [modifier] with custom sizing.
 */
@Composable
public fun LinearIndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = OrbitTheme.colors.primary.strong,
    backgroundColor: Color = OrbitTheme.colors.surface.normal,
) {
    val infiniteTransition = rememberInfiniteTransition()
    // Fractional position of the 'head' and 'tail' of the two lines drawn. I.e if the head is 0.8
    // and the tail is 0.2, there is a line drawn from between 20% along to 80% along the total
    // width.
    val firstLineHead by infiniteTransition.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = LinearAnimationDuration
                0f at FirstLineHeadDelay with FirstLineHeadEasing
                1f at FirstLineHeadDuration + FirstLineHeadDelay
            },
        ),
    )
    val firstLineTail by infiniteTransition.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = LinearAnimationDuration
                0f at FirstLineTailDelay with FirstLineTailEasing
                1f at FirstLineTailDuration + FirstLineTailDelay
            },
        ),
    )
    val secondLineHead by infiniteTransition.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = LinearAnimationDuration
                0f at SecondLineHeadDelay with SecondLineHeadEasing
                1f at SecondLineHeadDuration + SecondLineHeadDelay
            },
        ),
    )
    val secondLineTail by infiniteTransition.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = LinearAnimationDuration
                0f at SecondLineTailDelay with SecondLineTailEasing
                1f at SecondLineTailDuration + SecondLineTailDelay
            },
        ),
    )
    Canvas(
        modifier
            .height(LinearProgressIndicatorDefaultHeight)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(backgroundColor)
            .progressSemantics(),
    ) {
        if (firstLineHead - firstLineTail > 0) {
            drawLinearIndicator(
                startFraction = firstLineHead,
                endFraction = firstLineTail,
                color = color,
            )
        }
        if ((secondLineHead - secondLineTail) > 0) {
            drawLinearIndicator(
                startFraction = secondLineHead,
                endFraction = secondLineTail,
                color = color,
            )
        }
    }
}

// Indeterminate linear indicator transition specs
// Total duration for one cycle
private const val LinearAnimationDuration = 1800

// Duration of the head and tail animations for both lines
private const val FirstLineHeadDuration = 750
private const val FirstLineTailDuration = 850
private const val SecondLineHeadDuration = 567
private const val SecondLineTailDuration = 533

// Delay before the start of the head and tail animations for both lines
private const val FirstLineHeadDelay = 0
private const val FirstLineTailDelay = 333
private const val SecondLineHeadDelay = 1000
private const val SecondLineTailDelay = 1267

private val FirstLineHeadEasing = CubicBezierEasing(0.2f, 0f, 0.8f, 1f)
private val FirstLineTailEasing = CubicBezierEasing(0.4f, 0f, 1f, 1f)
private val SecondLineHeadEasing = CubicBezierEasing(0f, 0f, 0.65f, 1f)
private val SecondLineTailEasing = CubicBezierEasing(0.1f, 0f, 0.45f, 1f)

@OrbitPreviews
@Composable
internal fun LinearIndeterminateProgressIndicatorPreview() {
    Preview {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            LinearIndeterminateProgressIndicator()
            LinearIndeterminateProgressIndicator(
                color = OrbitTheme.colors.success.normal,
                backgroundColor = OrbitTheme.colors.surface.subtle,
            )
            LinearIndeterminateProgressIndicator(
                modifier = Modifier.height(6.dp),
                color = OrbitTheme.colors.primary.normal,
                backgroundColor = OrbitTheme.colors.primary.subtle,
            )
            CompositionLocalProvider(
                LocalLayoutDirection provides when (LocalLayoutDirection.current) {
                    LayoutDirection.Ltr -> LayoutDirection.Rtl
                    LayoutDirection.Rtl -> LayoutDirection.Ltr
                },
            ) {
                LinearIndeterminateProgressIndicator()
            }
        }
    }
}
