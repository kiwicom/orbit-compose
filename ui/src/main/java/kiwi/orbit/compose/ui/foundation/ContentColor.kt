@file:Suppress("Dependency")

package kiwi.orbit.compose.ui.foundation

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import kiwi.orbit.compose.ui.OrbitTheme

public fun Colors.contentColorFor(color: Color): Color =
    surface.contentColorFor(color, content)
        ?: primary.contentColorFor(color)
        ?: interactive.contentColorFor(color)
        ?: success.contentColorFor(color)
        ?: warning.contentColorFor(color)
        ?: critical.contentColorFor(color)
        ?: Color.Unspecified

private fun SurfaceColors.contentColorFor(
    color: Color,
    contentColors: ContentColors,
): Color? =
    when (color) {
        main -> contentColors.normal
        background -> contentColors.normal
        strong -> contentColors.normal
        else -> null
    }

private fun FeatureColors.contentColorFor(color: Color): Color? =
    when (color) {
        main -> onMain
        mainAlt -> onMain
        strong -> onMain
        subtle -> strong
        subtleAlt -> mainAlt
        else -> null
    }

@Composable
public fun contentColorFor(backgroundColor: Color): Color =
    OrbitTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

public val LocalContentColor: ProvidableCompositionLocal<Color> = LocalContentColor
