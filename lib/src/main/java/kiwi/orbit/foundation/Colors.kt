package kiwi.orbit.foundation

import androidx.compose.foundation.contentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.useOrElse
import kiwi.orbit.OrbitTheme
import androidx.compose.material.Colors as MaterialColors

/**
 * @property surface Color used for surfaces such as cards and sheets over the background.
 * @property surfaceBackground Color used for background, behind scrollable content.
 * @property surfaceSecondary Color used for surfaces with subtle emphasis such as buttons over an other surface color.
 * @property surfaceDisabled Color used for disabled surfaces, such as buttons.
 * @property surfaceFgPrimary Color used for text & icons display on top of a surface color, strong accent.
 * @property surfaceFgSecondary Color used for text & icons display on top of a surface color, medium accent.
 * @property surfaceFgTertiary Color used for text & icons display on top of a surface color, weak accent.
 * @property primary Primary app color, used for navigation and CTA actions. May be used for both background & foreground (text & icon) colors.
 * @property primaryFg Color used for text & icons display on top of the [primary] color.
 * @property primaryBg Color used as primary background with weak accent. Use [primary] as its foreground.
 * @property primaryVariant Color used used as a [primary]'s variant to distinguish two elements. Internal.
 * @property secondary Secondary app color, used for dynamic state and actions. May be used for both background & foreground (text & icon) colors.
 * @property secondaryFg Color used for text & icons display on top of the [secondary] color.
 * @property secondaryBg Color used as secondary background with weak accent. Use [secondary] as its foreground.
 * @property secondaryVariant Color used used as a [secondary]'s variant to distinguish two elements. Internal.
 * @property success Color used for success state. May be used for both background & foreground (text & icon) colors.
 * @property successFg Color used for text & icons display on top of the [success] color.
 * @property successBg Color used as success background with weak accent. Use [success] as its foreground.
 * @property warning Color used for warning state. May be used for both background & foreground (text & icon) colors.
 * @property warningFg Color used for text & icons display on top of the [warning] color.
 * @property warningBg Color used as warning background with weak accent. Use [warning] as its foreground.
 * @property critical Color used for warning state. May be used for both background & foreground (text & icon) colors.
 * @property criticalFg Color used for text & icons display on top of the [critical] color.
 * @property criticalBg Color used as critical background with weak accent. Use [critical] as its foreground.
 * @property isLight Whether this Colors is considered as a 'light' or 'dark' set of colors. This affects elevation provider's behavior.
 */
// TODO: consider using mutable state as properties to optimize recomposition when one particular color is changed.
@Immutable
data class Colors(
    val surface: Color,
    val surfaceBackground: Color,
    val surfaceSecondary: Color,
    val surfaceDisabled: Color,
    val surfaceFgPrimary: Color,
    val surfaceFgSecondary: Color,
    val surfaceFgTertiary: Color,

    val primary: Color,
    val primaryFg: Color,
    val primaryBg: Color,
    val primaryVariant: Color,

    val secondary: Color,
    val secondaryFg: Color,
    val secondaryBg: Color,
    val secondaryVariant: Color,

    val success: Color,
    val successFg: Color,
    val successBg: Color,

    val warning: Color,
    val warningFg: Color,
    val warningBg: Color,

    val critical: Color,
    val criticalFg: Color,
    val criticalBg: Color,

    val isLight: Boolean,
) {
    fun toMaterialColors(): MaterialColors {
        return MaterialColors(
            primary = primary,
            primaryVariant = primaryVariant,
            secondary = secondary,
            secondaryVariant = secondaryVariant,
            background = surfaceBackground,
            surface = surface,
            error = critical,
            onPrimary = primaryFg,
            onSecondary = secondaryFg,
            onBackground = surfaceFgPrimary,
            onSurface = surfaceFgPrimary,
            onError = criticalFg,
            isLight = isLight,
        )
    }
}

/**
 * Light Orbit color variant.
 */
fun lightColors(
    surface: Color = Color.White,
    surfaceBackground: Color = ColorTokens.CloudLight,
    surfaceSecondary: Color = ColorTokens.CloudDark,
    surfaceDisabled: Color = ColorTokens.CloudLight,
    surfaceFgPrimary: Color = ColorTokens.InkNormal,
    surfaceFgSecondary: Color = ColorTokens.InkLight,
    surfaceFgTertiary: Color = ColorTokens.InkLighter,

    primary: Color = ColorTokens.ProductNormal,
    primaryFg: Color = Color.White,
    primaryBg: Color = ColorTokens.ProductLight,
    primaryVariant: Color = ColorTokens.ProductDark,

    secondary: Color = ColorTokens.BlueNormal,
    secondaryFg: Color = Color.White,
    secondaryBg: Color = ColorTokens.BlueLight,
    secondaryVariant: Color = ColorTokens.BlueDark,

    success: Color = ColorTokens.GreenNormal,
    successFg: Color = ColorTokens.White,
    successBg: Color = ColorTokens.GreenLight,

    warning: Color = ColorTokens.OrangeNormal,
    warningFg: Color = ColorTokens.White,
    warningBg: Color = ColorTokens.GreenLight,

    critical: Color = ColorTokens.RedNormal,
    criticalFg: Color = Color.White,
    criticalBg: Color = ColorTokens.RedLight,
): Colors = Colors(
    surface,
    surfaceBackground,
    surfaceSecondary,
    surfaceDisabled,
    surfaceFgPrimary,
    surfaceFgSecondary,
    surfaceFgTertiary,
    primary,
    primaryFg,
    primaryBg,
    primaryVariant,
    secondary,
    secondaryFg,
    secondaryBg,
    secondaryVariant,
    success,
    successFg,
    successBg,
    warning,
    warningFg,
    warningBg,
    critical,
    criticalFg,
    criticalBg,
    true
)

fun Colors.contentColorFor(color: Color): Color {
    return when (color) {
        surface -> surfaceFgPrimary
        surfaceBackground -> surfaceFgPrimary
        surfaceSecondary -> surfaceFgPrimary
        primary -> primaryFg
        primaryVariant -> primaryFg
        primaryBg -> primary
        secondary -> secondaryFg
        secondaryVariant -> secondaryFg
        secondaryBg -> secondary
        success -> successFg
        successBg -> success
        warning -> warningFg
        warningBg -> warning
        critical -> criticalFg
        criticalBg -> critical
        else -> Color.Unset
    }
}

@Composable
fun contentColorFor(color: Color) =
    OrbitTheme.colors.contentColorFor(color).useOrElse { contentColor() }

internal val ColorsAmbient = staticAmbientOf { lightColors() }
