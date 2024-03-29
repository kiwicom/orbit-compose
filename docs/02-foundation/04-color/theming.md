---
title: Android Theming
---

## Material 2 Theming

Orbit Compose is based on Material 3 theme (library). If you need to use Orbit Compose together with Material 2, setup Material 2 theme on your own. To do so, apply `MaterialTheme` composable with new sets of colors and others. Use this conversion function to make Material 2 similar to the Orbit Theme.

```kotlin
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.Colors
import kiwi.orbit.compose.ui.foundation.Shapes
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.createTypography
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
fun App() {
    // your Orbit Theme setup
    val colors = lightColors()
    val typography = remember { createTypography() }
    val shapes = remember { Shapes() }

    MaterialTheme(
        colors = colors.toMaterial2(),
        typography = typography.toMaterial2(),
        shapes = shapes.toMaterial2(),
    ) {
        OrbitTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
        ) {
            // ...
        }
    }
}

private fun Colors.toMaterial2(): androidx.compose.material.Colors =
    androidx.compose.material.Colors(
        primary = primary.normal,
        primaryVariant = primary.strong,
        secondary = info.normal,
        secondaryVariant = info.strong,
        background = surface.subtle,
        surface = surface.main,
        error = critical.normal,
        onPrimary = primary.onNormal,
        onSecondary = info.onNormal,
        onBackground = content.normal,
        onSurface = content.normal,
        onError = critical.onNormal,
        isLight = isLight,
    )

private fun Typography.toMaterial2(): androidx.compose.material.Typography =
    androidx.compose.material.Typography(
        defaultFontFamily = title1.fontFamily ?: FontFamily.Default,
        h1 = title1.copy(fontSize = 96.sp, lineHeight = TextUnit.Unspecified),
        h2 = title1.copy(fontSize = 60.sp, lineHeight = TextUnit.Unspecified),
        h3 = title1.copy(fontSize = 38.sp, lineHeight = TextUnit.Unspecified),
        h4 = title1,
        h5 = title2,
        h6 = title3,
        subtitle1 = title4,
        subtitle2 = title5,
        overline = title6,
        body1 = bodyNormal,
        body2 = bodySmall,
        button = title5,
        caption = bodySmall,
    )

private fun Shapes.toMaterial2(): androidx.compose.material.Shapes =
    androidx.compose.material.Shapes(
        small = small,
        medium = normal,
        large = large,
    )
```
