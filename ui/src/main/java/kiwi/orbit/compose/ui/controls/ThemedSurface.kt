package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ThemedSurface(
    subtle: Boolean,
    shape: Shape,
    modifier: Modifier = Modifier,
    borderStrokeWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit,
) {
    ThemedSurface(
        backgroundColor = when (subtle) {
            true -> OrbitTheme.colors.primary.subtle
            false -> OrbitTheme.colors.primary.normal
        },
        backgroundBrush = null,
        border = when (subtle) {
            true -> BorderStroke(borderStrokeWidth, OrbitTheme.colors.primary.subtleAlt)
            false -> null
        },
        shape = shape,
        contentPadding = contentPadding,
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content,
    )
}

@Composable
public fun ThemedSurface(
    backgroundColor: Color,
    shape: Shape,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush? = null,
    border: BorderStroke? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    contentPadding: PaddingValues = PaddingValues(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit,
) {
    // remove style color to fallback to proper LocalContentColor
    val textStyle = LocalTextStyle.current.copy(
        color = Color.Unspecified,
    )
    CompositionLocalProvider(
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalContentColor provides contentColor,
        LocalTextStyle provides textStyle,
    ) {
        Row(
            modifier = modifier
                .then(if (border != null) Modifier.border(border, shape) else Modifier)
                .background(backgroundColor, backgroundBrush, shape)
                .padding(contentPadding),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content,
        )
    }
}

private fun Modifier.background(color: Color, brush: Brush?, shape: Shape): Modifier =
    when (brush) {
        null -> background(color, shape)
        else -> background(brush, shape)
    }
