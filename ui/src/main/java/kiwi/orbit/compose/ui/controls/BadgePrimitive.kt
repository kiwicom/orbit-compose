package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun BadgePrimitive(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Unspecified,
    contentColor: Color = contentColorFor(backgroundColor),
    backgroundBrush: Brush? = null,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    ThemedSurface(
        modifier = modifier,
        backgroundColor = backgroundColor,
        backgroundBrush = backgroundBrush,
        contentColor = contentColor,
        border = when (borderColor) {
            Color.Unspecified -> null
            else -> BorderStroke(BadgeStrokeWidth, borderColor)
        },
        shape = BadgeShape,
        contentPadding = BadgeContentPadding,
        verticalAlignment = BadgeAlignment,
        horizontalArrangement = BadgeArrangement,
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodySmallMedium) {
            icon()
            content()
        }
    }
}

internal val BadgeAlignment = Alignment.CenterVertically
internal val BadgeArrangement = Arrangement.spacedBy(4.dp)
internal val BadgeContentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
internal val BadgeShape = RoundedCornerShape(50)
internal val BadgeStrokeWidth = 0.5.dp
