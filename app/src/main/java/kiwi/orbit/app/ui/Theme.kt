package kiwi.orbit.app.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import kiwi.orbit.foundation.colors.Color


private val DarkColorPalette = darkColors(
    primary = Color.ProductNormal,
    primaryVariant = Color.ProductDark,
    secondary = Color.ProductDarker
)

private val LightColorPalette = lightColors(
    primary = Color.ProductNormal,
    primaryVariant = Color.ProductDark,
    secondary = Color.ProductDarker
)

@Composable
fun DemoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}