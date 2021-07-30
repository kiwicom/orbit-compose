package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

public fun darkColors(
    surface: SurfaceColors = darkSurfaceColors(),
    content: ContentColors = darkContentColors(),
    primary: FeatureColors = darkPrimaryColors(),
    interactive: FeatureColors = darkInteractiveColors(),
    success: FeatureColors = darkSuccessColors(),
    warning: FeatureColors = darkWarningColors(),
    critical: FeatureColors = darkCriticalColors(),
): Colors = Colors(
    surface = surface,
    content = content,
    primary = primary,
    interactive = interactive,
    success = success,
    warning = warning,
    critical = critical,
    isLight = false,
)

public fun darkSurfaceColors(
    background: Color = ColorTokens.White.invert(),
    main: Color = ColorTokens.CloudLight.invert(),
    subtle: Color = ColorTokens.CloudNormal.invert(),
    strong: Color = ColorTokens.CloudDark.invert(),
    disabled: Color = ColorTokens.CloudNormal.invert(),
): SurfaceColors = SurfaceColors(
    background = background,
    main = main,
    subtle = subtle,
    strong = strong,
    disabled = disabled,
)

public fun darkContentColors(
    normal: Color = ColorTokens.InkNormal.invert(),
    minor: Color = ColorTokens.InkLight.invert(),
    subtle: Color = ColorTokens.InkLighter.invert(),
    disabled: Color = ColorTokens.InkLighterHover.invert(),
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    disabled = disabled,
)

public fun darkPrimaryColors(
    main: Color = ColorTokens.ProductDark,
    mainAlt: Color = ColorTokens.ProductDarkHover,
    subtle: Color = ColorTokens.ProductLight.invert(),
    subtleAlt: Color = ColorTokens.ProductLightHover.invert(),
    strong: Color = ColorTokens.ProductNormal,
    onMain: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    main = main,
    mainAlt = mainAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onMain = onMain,
)

public fun darkInteractiveColors(
    main: Color = ColorTokens.BlueDark,
    mainAlt: Color = ColorTokens.BlueDarkHover,
    subtle: Color = ColorTokens.BlueLight.invert(),
    subtleAlt: Color = ColorTokens.BlueLightHover.invert(),
    strong: Color = ColorTokens.BlueNormal,
    onMain: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    main = main,
    mainAlt = mainAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onMain = onMain,
)

public fun darkSuccessColors(
    main: Color = ColorTokens.GreenDark,
    mainAlt: Color = ColorTokens.GreenDarkHover,
    subtle: Color = ColorTokens.GreenLight.invert(),
    subtleAlt: Color = ColorTokens.GreenLightHover.invert(),
    strong: Color = ColorTokens.GreenNormal,
    onMain: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    main = main,
    mainAlt = mainAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onMain = onMain,
)

public fun darkWarningColors(
    main: Color = ColorTokens.OrangeDark,
    mainAlt: Color = ColorTokens.OrangeDarkHover,
    subtle: Color = ColorTokens.OrangeLight.invert(),
    subtleAlt: Color = ColorTokens.OrangeLightHover.invert(),
    strong: Color = ColorTokens.OrangeNormal,
    onMain: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    main = main,
    mainAlt = mainAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onMain = onMain,
)

public fun darkCriticalColors(
    main: Color = ColorTokens.RedDark,
    mainAlt: Color = ColorTokens.RedDarkHover,
    subtle: Color = ColorTokens.RedLight.invert(),
    subtleAlt: Color = ColorTokens.RedLightHover.invert(),
    strong: Color = ColorTokens.RedNormal,
    onMain: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    main = main,
    mainAlt = mainAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onMain = onMain,
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun Color.invert(): Color {
    val hsl = floatArrayOf(0f, 0f, 0f)
    ColorUtils.colorToHSL((value shr 32).toInt(), hsl)
    hsl[2] = 1 - hsl[2]
    val colorInt = ColorUtils.HSLToColor(hsl)
    return Color(colorInt)
}
