package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Indeterminate circular progress indicator.
 *
 * Progress indicators express an unspecified wait time or display the duration of a process.
 *
 * Implementation just wraps Material's [androidx.compose.material3.CircularProgressIndicator].
 *
 * @param modifier the [Modifier] to be applied to this progress indicator
 * @param color color of this progress indicator
 * @param strokeWidth stroke width of this progress indicator
 */
@Composable
public fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = CircularProgressIndicatorDefaults.circularColor,
    strokeWidth: Dp = CircularProgressIndicatorDefaults.circularStrokeWidth,
) {
    androidx.compose.material3.CircularProgressIndicator(
        modifier,
        color,
        strokeWidth,
        strokeCap = StrokeCap.Round,
    )
}

@OrbitPreviews
@Composable
internal fun CircularProgressIndicatorPreview() {
    Preview {
        CircularProgressIndicator(
            color = OrbitTheme.colors.primary.normal,
            strokeWidth = 2.dp,
        )
    }
}

public object CircularProgressIndicatorDefaults {
    public val circularColor: Color
        @Composable get() = OrbitTheme.colors.primary.normal

    public val circularStrokeWidth: Dp = 4.0.dp
}
