package kiwi.orbit.compose.ui.controls

import androidx.annotation.FloatRange
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Linear progress indicator.
 *
 * Renders progress indicator in maximum available width with animated progress change.
 *
 * - To change the height or width, pass [modifier] with custom sizing.
 * - To disable the animation, use [androidx.compose.animation.core.snap] animation spec.
 */
@Composable
public fun LinearProgressIndicator(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    modifier: Modifier = Modifier,
    color: Color = OrbitTheme.colors.primary.strong,
    backgroundColor: Color = OrbitTheme.colors.surface.normal,
    progressAnimationSpec: AnimationSpec<Float> = SpringSpec(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessVeryLow,
        // The default threshold is 0.01, or 1% of the overall progress range, which is quite
        // large and noticeable. We purposefully choose a smaller threshold.
        visibilityThreshold = 1 / 1000f,
    ),
) {
    val animatedProgress by animateFloatAsState(progress, progressAnimationSpec)
    androidx.compose.material3.LinearProgressIndicator(
        progress = animatedProgress,
        modifier = modifier.fillMaxWidth(),
        color = color,
        trackColor = backgroundColor,
        strokeCap = StrokeCap.Round,
    )
}

@OrbitPreviews
@Composable
internal fun LinearProgressIndicatorPreview() {
    Preview {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            LinearProgressIndicator(1f)
            LinearProgressIndicator(0f)
            LinearProgressIndicator(
                progress = 0.5f,
                color = OrbitTheme.colors.success.normal,
                backgroundColor = OrbitTheme.colors.surface.subtle,
            )
            LinearProgressIndicator(
                modifier = Modifier.height(6.dp),
                progress = 0.5f,
                color = OrbitTheme.colors.primary.normal,
                backgroundColor = OrbitTheme.colors.primary.subtle,
            )
        }
    }
}
