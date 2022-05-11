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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme

@Composable
public fun LinearProgressIndicator(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = OrbitTheme.colors.primary.strong,
    trackColor: Color = OrbitTheme.colors.surface.strong,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    Canvas(
        modifier = modifier
            .height(4.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(trackColor)
            .progressSemantics(animatedProgress),
    ) {
        drawRoundRect(
            color = indicatorColor,
            size = Size(animatedProgress * size.width, size.height),
            cornerRadius = CornerRadius(size.height / 2f, size.height / 2f),
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLinearProgressIndicator() {
    OrbitTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                LinearProgressIndicator(1f)
                LinearProgressIndicator(0f)
                LinearProgressIndicator(
                    progress = 0.5f,
                    indicatorColor = OrbitTheme.colors.success.main,
                    trackColor = OrbitTheme.colors.surface.subtle,
                )
            }
        }
    }
}
