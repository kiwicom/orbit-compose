package kiwi.orbit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.LocalElevationEnabled
import kiwi.orbit.foundation.contentColorFor

@Composable
public fun ThemedSurface(
    subtle: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit,
) {
    val backgroundColor = when (subtle) {
        true -> OrbitTheme.colors.surface
        false -> OrbitTheme.colors.primary
    }
    val contentColor = contentColorFor(backgroundColor)

    val surfaceModifier = when (subtle) {
        true -> {
            Modifier
                .border(1.dp, OrbitTheme.colors.primaryAltSubtle, shape)
                .background(backgroundColor, shape)
        }
        false -> {
            Modifier
                .background(backgroundColor, shape)
        }
    }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalContentAlpha provides contentColor.alpha,
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
