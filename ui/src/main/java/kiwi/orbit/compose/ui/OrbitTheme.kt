package kiwi.orbit.compose.ui

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import kiwi.orbit.compose.ui.foundation.Colors
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalShapes
import kiwi.orbit.compose.ui.foundation.LocalTypography
import kiwi.orbit.compose.ui.foundation.Shapes
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.rememberTextSelectionColors
import kiwi.orbit.compose.ui.foundation.updateColorsFrom

@Composable
public fun OrbitTheme(
    colors: Colors = OrbitTheme.colors,
    typography: Typography = OrbitTheme.typography,
    shapes: Shapes = OrbitTheme.shapes,
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }.apply {
        updateColorsFrom(colors)
    }
    val selectionColors = rememberTextSelectionColors(rememberedColors)

    MaterialTheme(
        colors = rememberedColors.toMaterialColors(),
        typography = typography.toMaterialTypography(),
        shapes = shapes.toMaterialShapes(),
    ) {
        CompositionLocalProvider(
            // Orbit
            LocalColors provides rememberedColors,
            LocalContentEmphasis provides ContentEmphasis.Normal,
            LocalShapes provides shapes,
            LocalTypography provides typography,
            // Foundation
            LocalTextSelectionColors provides selectionColors,
            content = content
        )
    }
}

public object OrbitTheme {
    public val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    public val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    public val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
