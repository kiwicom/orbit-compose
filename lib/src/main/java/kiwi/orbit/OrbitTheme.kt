package kiwi.orbit

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import kiwi.orbit.foundation.AmbientColors
import kiwi.orbit.foundation.AmbientTypography
import kiwi.orbit.foundation.Colors
import kiwi.orbit.foundation.Shapes
import kiwi.orbit.foundation.Typography

@Composable
public fun OrbitTheme(
    colors: Colors = OrbitTheme.colors,
    typography: Typography = OrbitTheme.typography,
    content: @Composable () -> Unit,
) {
    Providers(
        AmbientColors provides colors,
        AmbientTypography provides typography
    ) {
        ProvideTextStyle(value = OrbitTheme.typography.bodyNormal) {
            MaterialTheme(
                colors = colors.toMaterialColors(),
                typography = typography.toMaterialTypography(),
                shapes = Shapes,
                content = content,
            )
        }
    }
}

public object OrbitTheme {
    /**
     * Retrieves the current [Colors] at the call site's position in the hierarchy.
     */
    @get:Composable
    public val colors: Colors
        get() = AmbientColors.current

    /**
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     */
    @get:Composable
    public val typography: Typography
        get() = AmbientTypography.current
}
