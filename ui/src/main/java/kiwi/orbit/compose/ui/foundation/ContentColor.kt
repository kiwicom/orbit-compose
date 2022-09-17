@file:Suppress("Dependency")

package kiwi.orbit.compose.ui.foundation

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import kiwi.orbit.compose.ui.OrbitTheme

public fun Colors.contentColorFor(color: Color): Color =
    surface.contentColorFor(color, content)
        ?: content.contentColorFor(color, surface)
        ?: primary.contentColorFor(color)
        ?: info.contentColorFor(color)
        ?: success.contentColorFor(color)
        ?: warning.contentColorFor(color)
        ?: critical.contentColorFor(color)
        ?: bundle.contentColorFor(color)
        ?: Color.Unspecified

public fun Colors.contentColorFor(brush: Brush): Color =
    when (brush) {
        bundle.basicGradient -> bundle.onBasic
        bundle.mediumGradient -> bundle.onMedium
        bundle.topGradient -> bundle.onTop
        else -> Color.Unspecified
    }

private fun SurfaceColors.contentColorFor(
    color: Color,
    contentColors: ContentColors,
): Color? =
    when (color) {
        main -> contentColors.normal
        subtle -> contentColors.normal
        subtleAlt -> contentColors.normal
        normal -> contentColors.normal
        normalAlt -> contentColors.normal
        strong -> contentColors.normal
        strongAlt -> contentColors.normal
        else -> null
    }

private fun ContentColors.contentColorFor(
    color: Color,
    surfaceColors: SurfaceColors,
): Color? =
    when (color) {
        normal -> surfaceColors.main
        else -> null
    }

private fun FeatureColors.contentColorFor(color: Color): Color? =
    when (color) {
        normal -> onNormal
        normalAlt -> onNormal
        subtle -> strong
        subtleAlt -> strongAlt
        strong -> onNormal
        strongAlt -> onNormal
        else -> null
    }

private fun BundleColors.contentColorFor(color: Color): Color? =
    when (color) {
        basic -> onBasic
        medium -> onMedium
        top -> onTop
        else -> null
    }

@ReadOnlyComposable
@Composable
public fun contentColorFor(backgroundColor: Color): Color =
    OrbitTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

@ReadOnlyComposable
@Composable
public fun contentColorFor(backgroundColor: Brush): Color =
    OrbitTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

public val LocalContentColor: ProvidableCompositionLocal<Color> = LocalContentColor
