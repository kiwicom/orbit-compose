package kiwi.orbit

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import kiwi.orbit.foundation.Colors
import kiwi.orbit.foundation.ColorsAmbient
import kiwi.orbit.foundation.Shapes
import kiwi.orbit.foundation.Typography
import kiwi.orbit.foundation.TypographyAmbient

@Composable
fun OrbitTheme(
    colors: Colors = OrbitTheme.colors,
    typography: Typography = OrbitTheme.typography,
    content: @Composable () -> Unit,
) {
    Providers(
        ColorsAmbient provides colors,
        TypographyAmbient provides typography
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

object OrbitTheme {
    /**
     * Retrieves the current [Colors] at the call site's position in the hierarchy.
     */
    @Composable
    val colors: Colors
        get() = ColorsAmbient.current

    /**
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     */
    @Composable
    val typography: Typography
        get() = TypographyAmbient.current
}
