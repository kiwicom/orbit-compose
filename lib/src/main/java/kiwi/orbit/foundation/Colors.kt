package kiwi.orbit.foundation

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.core.graphics.ColorUtils
import kiwi.orbit.OrbitTheme
import androidx.compose.material.Colors as MaterialColors

@Suppress("MemberVisibilityCanBePrivate")
@Stable
public class Colors(
    surfaceBackground: Color,
    surface: Color,
    surfaceAlt: Color,

    surfaceContent: Color,
    surfaceContentAlt: Color,
    surfaceContentTertiary: Color,

    primary: Color,
    primaryContent: Color,
    primarySubtle: Color,
    primaryAlt: Color,
    primaryAltSubtle: Color,

    interactive: Color,
    interactiveContent: Color,
    interactiveSubtle: Color,
    interactiveAlt: Color,
    interactiveAltSubtle: Color,

    success: Color,
    successContent: Color,
    successSubtle: Color,
    successAlt: Color,
    successAltSubtle: Color,

    warning: Color,
    warningSubtle: Color,
    warningContent: Color,
    warningAlt: Color,
    warningAltSubtle: Color,

    critical: Color,
    criticalSubtle: Color,
    criticalContent: Color,
    criticalAlt: Color,
    criticalAltSubtle: Color,

    isLight: Boolean,
) {
    // @formatter:off
    public var surfaceBackground: Color by mutableStateOf(surfaceBackground, structuralEqualityPolicy()); internal set
    public var surface: Color by mutableStateOf(surface, structuralEqualityPolicy()); internal set
    public var surfaceAlt: Color by mutableStateOf(surfaceAlt, structuralEqualityPolicy()); internal set

    public var surfaceContent: Color by mutableStateOf(surfaceContent, structuralEqualityPolicy()); internal set
    public var surfaceContentAlt: Color by mutableStateOf(surfaceContentAlt, structuralEqualityPolicy()); internal set
    public var surfaceContentTertiary: Color by mutableStateOf(surfaceContentTertiary, structuralEqualityPolicy()); internal set

    public var primary: Color by mutableStateOf(primary, structuralEqualityPolicy()); internal set
    public var primaryContent: Color by mutableStateOf(primaryContent, structuralEqualityPolicy()); internal set
    public var primarySubtle: Color by mutableStateOf(primarySubtle, structuralEqualityPolicy()); internal set
    public var primaryAlt: Color by mutableStateOf(primaryAlt, structuralEqualityPolicy()); internal set
    public var primaryAltSubtle: Color by mutableStateOf(primaryAltSubtle, structuralEqualityPolicy()); internal set

    public var interactive: Color by mutableStateOf(interactive, structuralEqualityPolicy()); internal set
    public var interactiveContent: Color by mutableStateOf(interactiveContent, structuralEqualityPolicy()); internal set
    public var interactiveSubtle: Color by mutableStateOf(interactiveSubtle, structuralEqualityPolicy()); internal set
    public var interactiveAlt: Color by mutableStateOf(interactiveAlt, structuralEqualityPolicy()); internal set
    public var interactiveAltSubtle: Color by mutableStateOf(interactiveAltSubtle, structuralEqualityPolicy()); internal set

    public var success: Color by mutableStateOf(success, structuralEqualityPolicy()); internal set
    public var successContent: Color by mutableStateOf(successContent, structuralEqualityPolicy()); internal set
    public var successSubtle: Color by mutableStateOf(successSubtle, structuralEqualityPolicy()); internal set
    public var successAlt: Color by mutableStateOf(successAlt, structuralEqualityPolicy()); internal set
    public var successAltSubtle: Color by mutableStateOf(successAltSubtle, structuralEqualityPolicy()); internal set

    public var warning: Color by mutableStateOf(warning, structuralEqualityPolicy()); internal set
    public var warningContent: Color by mutableStateOf(warningContent, structuralEqualityPolicy()); internal set
    public var warningSubtle: Color by mutableStateOf(warningSubtle, structuralEqualityPolicy()); internal set
    public var warningAlt: Color by mutableStateOf(warningAlt, structuralEqualityPolicy()); internal set
    public var warningAltSubtle: Color by mutableStateOf(warningAltSubtle, structuralEqualityPolicy()); internal set

    public var critical: Color by mutableStateOf(critical, structuralEqualityPolicy()); internal set
    public var criticalContent: Color by mutableStateOf(criticalContent, structuralEqualityPolicy()); internal set
    public var criticalSubtle: Color by mutableStateOf(criticalSubtle, structuralEqualityPolicy()); internal set
    public var criticalAlt: Color by mutableStateOf(criticalAlt, structuralEqualityPolicy()); internal set
    public var criticalAltSubtle: Color by mutableStateOf(criticalAltSubtle, structuralEqualityPolicy()); internal set
    // @formatter:on

    public var isLight: Boolean by mutableStateOf(isLight, structuralEqualityPolicy()); internal set

    public fun copy(
        surfaceBackground: Color = this.surfaceBackground,
        surface: Color = this.surface,
        surfaceAlt: Color = this.surfaceAlt,

        surfaceContent: Color = this.surfaceContent,
        surfaceContentAlt: Color = this.surfaceContentAlt,
        surfaceContentTertiary: Color = this.surfaceContentTertiary,

        primary: Color = this.primary,
        primaryContent: Color = this.primaryContent,
        primarySubtle: Color = this.primarySubtle,
        primaryAlt: Color = this.primaryAlt,
        primaryAltSubtle: Color = this.primaryAltSubtle,

        interactive: Color = this.interactive,
        interactiveContent: Color = this.interactiveContent,
        interactiveSubtle: Color = this.interactiveSubtle,
        interactiveAlt: Color = this.interactiveAlt,
        interactiveAltSubtle: Color = this.interactiveAltSubtle,

        success: Color = this.success,
        successContent: Color = this.successContent,
        successSubtle: Color = this.successSubtle,
        successAlt: Color = this.successAlt,
        successAltSubtle: Color = this.successAltSubtle,

        warning: Color = this.warning,
        warningSubtle: Color = this.warningSubtle,
        warningContent: Color = this.warningContent,
        warningAlt: Color = this.warningAlt,
        warningAltSubtle: Color = this.warningAltSubtle,

        critical: Color = this.critical,
        criticalSubtle: Color = this.criticalSubtle,
        criticalContent: Color = this.criticalContent,
        criticalAlt: Color = this.criticalAlt,
        criticalAltSubtle: Color = this.criticalAltSubtle,

        isLight: Boolean = this.isLight,
    ): Colors = Colors(
        surfaceBackground,
        surface,
        surfaceAlt,

        surfaceContent,
        surfaceContentAlt,
        surfaceContentTertiary,

        primary,
        primaryContent,
        primarySubtle,
        primaryAlt,
        primaryAltSubtle,

        interactive,
        interactiveContent,
        interactiveSubtle,
        interactiveAlt,
        interactiveAltSubtle,

        success,
        successContent,
        successSubtle,
        successAlt,
        successAltSubtle,

        warning,
        warningSubtle,
        warningContent,
        warningAlt,
        warningAltSubtle,

        critical,
        criticalSubtle,
        criticalContent,
        criticalAlt,
        criticalAltSubtle,

        isLight,
    )

    internal fun toMaterialColors(): MaterialColors {
        return MaterialColors(
            primary = primary,
            primaryVariant = primaryAlt,
            secondary = interactive,
            secondaryVariant = interactiveAlt,
            background = surfaceBackground,
            surface = surface,
            error = critical,
            onPrimary = primaryContent,
            onSecondary = interactiveContent,
            onBackground = surfaceContent,
            onSurface = surfaceContent,
            onError = criticalContent,
            isLight = isLight,
        )
    }
}

/**
 * Light Orbit color variant.
 */
public fun lightColors(
    surfaceBackground: Color = ColorTokens.CloudLight,
    surface: Color = ColorTokens.White,
    surfaceAlt: Color = ColorTokens.CloudDark,

    surfaceContent: Color = ColorTokens.InkNormal,
    surfaceContentAlt: Color = ColorTokens.InkLight,
    surfaceContentTertiary: Color = ColorTokens.InkLighter,

    primary: Color = ColorTokens.ProductNormal,
    primaryContent: Color = ColorTokens.White,
    primarySubtle: Color = ColorTokens.ProductLight,
    primaryAlt: Color = ColorTokens.ProductNormalHover,
    primaryAltSubtle: Color = ColorTokens.ProductLightHover,

    interactive: Color = ColorTokens.BlueNormal,
    interactiveContent: Color = ColorTokens.White,
    interactiveSubtle: Color = ColorTokens.BlueLight,
    interactiveAlt: Color = ColorTokens.BlueNormalHover,
    interactiveAltSubtle: Color = ColorTokens.BlueLightHover,

    success: Color = ColorTokens.GreenNormal,
    successContent: Color = ColorTokens.White,
    successSubtle: Color = ColorTokens.GreenLight,
    successAlt: Color = ColorTokens.GreenNormalHover,
    successAltSubtle: Color = ColorTokens.GreenLightHover,

    warning: Color = ColorTokens.OrangeNormal,
    warningContent: Color = ColorTokens.White,
    warningSubtle: Color = ColorTokens.OrangeLight,
    warningAlt: Color = ColorTokens.OrangeLightHover,
    warningAltSubtle: Color = ColorTokens.OrangeLightHover,

    critical: Color = ColorTokens.RedNormal,
    criticalContent: Color = Color.White,
    criticalSubtle: Color = ColorTokens.RedLight,
    criticalAlt: Color = ColorTokens.RedNormalHover,
    criticalAltSubtle: Color = ColorTokens.RedLightHover,
): Colors = Colors(
    surfaceBackground,
    surface,
    surfaceAlt,
    surfaceContent,
    surfaceContentAlt,
    surfaceContentTertiary,
    primary,
    primaryContent,
    primarySubtle,
    primaryAlt,
    primaryAltSubtle,
    interactive,
    interactiveContent,
    interactiveSubtle,
    interactiveAlt,
    interactiveAltSubtle,
    success,
    successContent,
    successSubtle,
    successAlt,
    successAltSubtle,
    warning,
    warningSubtle,
    warningContent,
    warningAlt,
    warningAltSubtle,
    critical,
    criticalSubtle,
    criticalContent,
    criticalAlt,
    criticalAltSubtle,
    true
)

@OptIn(ExperimentalUnsignedTypes::class)
internal fun Color.invert(): Color {
    val hsl = floatArrayOf(0f, 0f, 0f)
    ColorUtils.colorToHSL((value shr 32).toInt(), hsl)
    hsl[2] = 1 - hsl[2]
    val colorInt = ColorUtils.HSLToColor(hsl)
    return Color(colorInt)
}

/**
 * Dark Orbit color variant.
 */
public fun darkColors(
    surfaceBackground: Color = ColorTokens.CloudLight.invert(),
    surface: Color = ColorTokens.White.invert(),
    surfaceAlt: Color = ColorTokens.CloudDark.invert(),

    surfaceContent: Color = ColorTokens.InkNormal.invert(),
    surfaceContentAlt: Color = ColorTokens.InkLight.invert(),
    surfaceContentTertiary: Color = ColorTokens.InkLighter.invert(),

    primary: Color = ColorTokens.ProductDark,
    primaryContent: Color = ColorTokens.White.invert(),
    primarySubtle: Color = ColorTokens.ProductLight.invert(),
    primaryAlt: Color = ColorTokens.ProductDarkHover,
    primaryAltSubtle: Color = ColorTokens.ProductLightHover.invert(),

    interactive: Color = ColorTokens.BlueDark,
    interactiveContent: Color = ColorTokens.White.invert(),
    interactiveSubtle: Color = ColorTokens.BlueLight.invert(),
    interactiveAlt: Color = ColorTokens.BlueDarkHover,
    interactiveAltSubtle: Color = ColorTokens.BlueLightHover.invert(),

    success: Color = ColorTokens.GreenDark,
    successContent: Color = ColorTokens.White.invert(),
    successSubtle: Color = ColorTokens.GreenLight.invert(),
    successAlt: Color = ColorTokens.GreenDarkHover,
    successAltSubtle: Color = ColorTokens.GreenLightHover.invert(),

    warning: Color = ColorTokens.OrangeDark,
    warningContent: Color = ColorTokens.White.invert(),
    warningSubtle: Color = ColorTokens.OrangeLight.invert(),
    warningAlt: Color = ColorTokens.OrangeDarkHover,
    warningAltSubtle: Color = ColorTokens.OrangeLightHover.invert(),

    critical: Color = ColorTokens.RedDark,
    criticalContent: Color = Color.White.invert(),
    criticalSubtle: Color = ColorTokens.RedLight.invert(),
    criticalAlt: Color = ColorTokens.RedDarkHover,
    criticalAltSubtle: Color = ColorTokens.RedLightHover.invert(),
): Colors = Colors(
    surfaceBackground,
    surface,
    surfaceAlt,
    surfaceContent,
    surfaceContentAlt,
    surfaceContentTertiary,
    primary,
    primaryContent,
    primarySubtle,
    primaryAlt,
    primaryAltSubtle,
    interactive,
    interactiveContent,
    interactiveSubtle,
    interactiveAlt,
    interactiveAltSubtle,
    success,
    successContent,
    successSubtle,
    successAlt,
    successAltSubtle,
    warning,
    warningSubtle,
    warningContent,
    warningAlt,
    warningAltSubtle,
    critical,
    criticalSubtle,
    criticalContent,
    criticalAlt,
    criticalAltSubtle,
    false
)

public fun Colors.contentColorFor(color: Color): Color {
    return when (color) {
        surface -> surfaceContent
        surfaceBackground -> surfaceContent
        surfaceAlt -> surfaceContent
        primary -> primaryContent
        primarySubtle -> primary
        primaryAlt -> primaryContent
        primaryAltSubtle -> primaryAlt
        interactive -> interactiveContent
        interactiveSubtle -> interactive
        interactiveAlt -> interactiveContent
        interactiveAltSubtle -> interactiveAlt
        success -> successContent
        successSubtle -> success
        successAlt -> successContent
        successAltSubtle -> successAlt
        warning -> warningContent
        warningSubtle -> warning
        warningAlt -> warningContent
        warningAltSubtle -> warningAlt
        critical -> criticalContent
        criticalSubtle -> critical
        criticalAlt -> criticalContent
        criticalAltSubtle -> criticalAlt
        else -> Color.Unspecified
    }
}

@Composable
public fun contentColorFor(color: Color): Color =
    OrbitTheme.colors.contentColorFor(color).takeOrElse { LocalContentColor.current }

internal val LocalColors = staticCompositionLocalOf { lightColors() }
