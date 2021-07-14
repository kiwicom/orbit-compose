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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalElevationEnabled
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ThemedSurface(
    subtle: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = OrbitTheme.shapes.normal,
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
                .border(1.dp, OrbitTheme.colors.surface.strong, shape)
                .background(backgroundColor, shape)
        }
        false -> {
            Modifier
                .background(backgroundColor, shape)
        }
    }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalElevationEnabled provides false,
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
