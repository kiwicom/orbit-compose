package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun SeatLegendExtraLegroom(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    SeatLegend(
        color = OrbitTheme.colors.info.normal.copy(alpha = 0.2f), // custom, missing theme color
        content = content,
        modifier = modifier,
    )
}

@Composable
public fun SeatLegendStandard(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    SeatLegend(
        color = OrbitTheme.colors.primary.normal.copy(alpha = 0.2f), // custom, missing theme color
        content = content,
        modifier = modifier,
    )
}

@Composable
public fun SeatLegendUnavailable(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    SeatLegend(
        color = OrbitTheme.colors.surface.strong.copy(alpha = 0.6f), // custom, missing theme color
        content = content,
        modifier = modifier,
    )
}

@Composable
private fun SeatLegend(
    color: Color,
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SeatLegendIcon(color)
        CompositionLocalProvider(
            LocalContentEmphasis provides ContentEmphasis.Minor,
            LocalTextStyle provides OrbitTheme.typography.bodyNormal,
        ) {
            content()
        }
    }
}

@Composable
private fun SeatLegendIcon(
    color: Color,
) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(16.dp)
            .height(20.dp)
            .clip(
                RoundedCornerShape(
                    topStart = SeatTopRadius,
                    topEnd = SeatTopRadius,
                    bottomStart = SeatBottomRadius,
                    bottomEnd = SeatBottomRadius,
                ),
            )
            .background(color),
    )
}

// Top radius of the legend icon
private val SeatTopRadius = 3.dp

// Bottom radius of the legend icon
private val SeatBottomRadius = 1.dp

@OrbitPreviews
@Composable
internal fun SeatLegendPreview() {
    Preview {
        SeatLegendStandard { Text("Standard") }
        SeatLegendExtraLegroom { Text("Extra Legroom") }
        SeatLegendUnavailable { Text("Unavailable") }
    }
}
