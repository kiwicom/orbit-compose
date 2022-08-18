package kiwi.orbit.compose.ui.controls

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Linear progress indicator.
 *
 * Renders [LinearProgressIndicatorDefaultHeight] tall rounded line in maximum available width.
 * To change the height or width, pass [modifier] with custom sizing.
 */
@Composable
public fun LinearProgressIndicator(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    modifier: Modifier = Modifier,
    color: Color = OrbitTheme.colors.primary.strong,
    backgroundColor: Color = OrbitTheme.colors.surface.strong,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )
    Canvas(
        modifier = modifier
            .height(LinearProgressIndicatorDefaultHeight)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(backgroundColor)
            .progressSemantics(progress.coerceIn(0f, 1f)),
    ) {
        drawLinearIndicator(
            color = color,
            endFraction = animatedProgress,
        )
    }
}

internal fun DrawScope.drawLinearIndicator(
    color: Color,
    startFraction: Float = 0f,
    endFraction: Float,
) {
    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * size.width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * size.width

    // Progress line
    drawRoundRect(
        color = color,
        topLeft = Offset(barStart, 0f),
        size = Size(barEnd - barStart, size.height),
        cornerRadius = CornerRadius(size.height / 2f, size.height / 2f),
    )
}

internal val LinearProgressIndicatorDefaultHeight = 4.dp

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
            CompositionLocalProvider(
                LocalLayoutDirection provides when (LocalLayoutDirection.current) {
                    LayoutDirection.Ltr -> LayoutDirection.Rtl
                    LayoutDirection.Rtl -> LayoutDirection.Ltr
                }
            ) {
                LinearProgressIndicator(0.5f)
            }
        }
    }
}
