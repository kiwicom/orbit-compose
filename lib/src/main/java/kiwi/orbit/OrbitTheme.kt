package kiwi.orbit

import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import kiwi.orbit.foundation.*

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
