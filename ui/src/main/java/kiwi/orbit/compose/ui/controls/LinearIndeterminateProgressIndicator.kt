package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Linear indeterminate progress indicator.
 *
 * Renders indeterminate progress indicator maximum available width.
 * To change the height or width, pass [modifier] with custom sizing.
 */
@Composable
public fun LinearIndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = OrbitTheme.colors.primary.strong,
    backgroundColor: Color = OrbitTheme.colors.surface.normal,
) {
    androidx.compose.material3.LinearProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        color = color,
        trackColor = backgroundColor,
        strokeCap = StrokeCap.Round,
    )
}

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
        }
    }
}
