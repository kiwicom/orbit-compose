package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

public fun darkColors(
    surface: SurfaceColors = darkSurfaceColors(),
    content: ContentColors = darkContentColors(),
    primary: FeatureColors = darkPrimaryColors(),
    info: FeatureColors = darkInfoColors(),
    success: FeatureColors = darkSuccessColors(),
    warning: FeatureColors = darkWarningColors(),
    critical: FeatureColors = darkCriticalColors(),
    bundle: BundleColors = darkBundleColors(),
): Colors = Colors(
    surface = surface,
    content = content,
    primary = primary,
    info = info,
    success = success,
    warning = warning,
    critical = critical,
    bundle = bundle,
    isLight = false,
)

public fun darkSurfaceColors(
    main: Color = ColorTokens.CloudLight.invert(),
    background: Color = ColorTokens.White.invert(),
    subtle: Color = ColorTokens.CloudNormal.invert(),
    strong: Color = ColorTokens.CloudDark.invert(),
    disabled: Color = ColorTokens.CloudDarker.invert(),
): SurfaceColors = SurfaceColors(
    main = main,
    background = background,
    subtle = subtle,
    strong = strong,
    disabled = disabled,
)

public fun darkContentColors(
    normal: Color = ColorTokens.InkNormal.invert(),
    minor: Color = ColorTokens.InkLight.invert(),
    subtle: Color = ColorTokens.InkLighter.invert(),
    highlight: Color = ColorTokens.ProductNormal,
    disabled: Color = ColorTokens.CloudDarkerHover.invert(),
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    highlight = highlight,
    disabled = disabled,
)

public fun darkPrimaryColors(
    normal: Color = ColorTokens.ProductDark,
    normalAlt: Color = ColorTokens.ProductDarkHover,
    subtle: Color = ColorTokens.ProductLight.invert(),
    subtleAlt: Color = ColorTokens.ProductLightHover.invert(),
    strong: Color = ColorTokens.ProductNormal,
    onNormal: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
)

public fun darkInfoColors(
    normal: Color = ColorTokens.BlueDark,
    normalAlt: Color = ColorTokens.BlueDarkHover,
    subtle: Color = ColorTokens.BlueLight.invert(),
    subtleAlt: Color = ColorTokens.BlueLightHover.invert(),
    strong: Color = ColorTokens.BlueNormal,
    onNormal: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
)

public fun darkSuccessColors(
    normal: Color = ColorTokens.GreenDark,
    normalAlt: Color = ColorTokens.GreenDarkHover,
    subtle: Color = ColorTokens.GreenLight.invert(),
    subtleAlt: Color = ColorTokens.GreenLightHover.invert(),
    strong: Color = ColorTokens.GreenNormal,
    onNormal: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
)

public fun darkWarningColors(
    normal: Color = ColorTokens.OrangeDark,
    normalAlt: Color = ColorTokens.OrangeDarkHover,
    subtle: Color = ColorTokens.OrangeLight.invert(),
    subtleAlt: Color = ColorTokens.OrangeLightHover.invert(),
    strong: Color = ColorTokens.OrangeNormal,
    onNormal: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
)

public fun darkCriticalColors(
    normal: Color = ColorTokens.RedDark,
    normalAlt: Color = ColorTokens.RedDarkHover,
    subtle: Color = ColorTokens.RedLight.invert(),
    subtleAlt: Color = ColorTokens.RedLightHover.invert(),
    strong: Color = ColorTokens.RedNormal,
    onNormal: Color = ColorTokens.White.invert(),
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
)

public fun darkBundleColors(
    basic: Color = ColorTokens.BundleBasic.invert(),
    basicGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleBasicStart.invert(),
        ColorTokens.BundleBasicEnd.invert()
    ),
    medium: Color = ColorTokens.BundleMedium.invert(),
    mediumGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleMediumStart.invert(),
        ColorTokens.BundleMediumEnd.invert()
    ),
    top: Color = ColorTokens.InkNormal.invert(),
    topGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleTopStart.invert(),
        ColorTokens.BundleTopEnd.invert()
    ),
    onBasic: Color = ColorTokens.White.invert(),
    onMedium: Color = ColorTokens.White.invert(),
    onTop: Color = ColorTokens.White.invert(),
): BundleColors = BundleColors(
    basic = basic,
    basicGradient = basicGradient,
    medium = medium,
    mediumGradient = mediumGradient,
    top = top,
    topGradient = topGradient,
    onBasic = onBasic,
    onMedium = onMedium,
    onTop = onTop,
)

private fun Color.invert(): Color {
    val hsl = floatArrayOf(0f, 0f, 0f)
    ColorUtils.colorToHSL((value shr 32).toInt(), hsl)
    hsl[2] = 1 - hsl[2]
    val colorInt = ColorUtils.HSLToColor(hsl)
    return Color(colorInt)
}
