package kiwi.orbit.compose.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import kiwi.orbit.compose.ui.foundation.Colors
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalElevationEnabled
import kiwi.orbit.compose.ui.foundation.LocalShapes
import kiwi.orbit.compose.ui.foundation.LocalTypography
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.Shapes
import kiwi.orbit.compose.ui.foundation.Typography
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
    val rippleIndication = rememberRipple()

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalContentColor provides colors.content.normal,
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalRippleTheme provides MaterialRippleTheme,
        LocalShapes provides shapes,
        LocalIndication provides rippleIndication,
        LocalTypography provides typography,
    ) {
        ProvideMergedTextStyle(typography.bodyNormal, content = content)
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

    public val elevationEnabled: Boolean
        @Composable
        @ReadOnlyComposable
        get() = LocalElevationEnabled.current
}

private object MaterialRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = OrbitTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = LocalContentColor.current,
        lightTheme = OrbitTheme.colors.isLight
    )
}
