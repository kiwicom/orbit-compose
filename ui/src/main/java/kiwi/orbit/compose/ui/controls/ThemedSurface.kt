package kiwi.orbit.compose.ui.controls

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ThemedSurface(
    subtle: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = OrbitTheme.shapes.normal,
    strokeWidth: Dp = 1.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit,
) {
    val backgroundColor = when (subtle) {
        true -> OrbitTheme.colors.surface.background
        false -> OrbitTheme.colors.primary.main
    }
    val contentColor = contentColorFor(backgroundColor)

    val surfaceModifier = when (subtle) {
        true -> {
            Modifier
                .border(strokeWidth, OrbitTheme.colors.surface.strong, shape)
                .background(backgroundColor, shape)
        }
        false -> {
            Modifier
                .background(backgroundColor, shape)
        }
    }

    // remove style color to fallback to proper LocalContentColor
    val textStyle = LocalTextStyle.current.copy(
        color = Color.Unspecified
    )

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides textStyle,
    ) {
        Row(
            modifier = modifier
                .then(surfaceModifier)
                .padding(contentPadding),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content,
        )
    }
}
