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
    main: Color = Color(0xFF111927),
    background: Color = Color(0xFF111927),
    subtle: Color = Color(0xFF303846),
    strong: Color = Color(0xFF343b42),
    disabled: Color = Color(0x885E6674),
): SurfaceColors = SurfaceColors(
    main = main,
    background = background,
    subtle = subtle,
    strong = strong,
    disabled = disabled,
)

public fun darkContentColors(
    normal: Color = Color(0xFFFFFFFF),
    minor: Color = Color(0xFFBAC7D5),
    subtle: Color = Color(0xFFA1AEBC),
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
    normal: Color = Color(0xFF00CBAE),
    normalAlt: Color = ColorTokens.ProductDarkHover,
    subtle: Color = Color(0xFF0A4038),
    subtleAlt: Color = Color(0xFF09332D),
    strong: Color = ColorTokens.ProductNormal,
    onNormal: Color = ColorTokens.White.invert(),
    onSubtle: Color = ColorTokens.White,
    onSubtleAlt: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
    onSubtle = onSubtle,
    onSubtleAlt = onSubtleAlt,
)

public fun darkInfoColors(
    normal: Color = Color(0xFF2AA0FE),
    normalAlt: Color = ColorTokens.BlueDarkHover,
    subtle: Color = Color(0xFF21425F),
    subtleAlt: Color = Color(0xFF3B5C79),
    strong: Color = ColorTokens.BlueNormal,
    onNormal: Color = ColorTokens.White.invert(),
    onSubtle: Color = ColorTokens.White,
    onSubtleAlt: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
    onSubtle = onSubtle,
    onSubtleAlt = onSubtleAlt,
)

public fun darkSuccessColors(
    normal: Color = Color(0xFF3BCE4E),
    normalAlt: Color = ColorTokens.GreenDarkHover,
    subtle: Color = Color(0xFF254B3C),
    subtleAlt: Color = Color(0xFF3F6556),
    strong: Color = ColorTokens.GreenNormal,
    onNormal: Color = ColorTokens.White.invert(),
    onSubtle: Color = ColorTokens.White,
    onSubtleAlt: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
    onSubtle = onSubtle,
    onSubtleAlt = onSubtleAlt,
)

public fun darkWarningColors(
    normal: Color = Color(0xFFFBA132),
    normalAlt: Color = ColorTokens.OrangeDarkHover,
    subtle: Color = Color(0xFF4B4236),
    subtleAlt: Color = Color(0xFF655C50),
    strong: Color = ColorTokens.OrangeNormal,
    onNormal: Color = ColorTokens.White.invert(),
    onSubtle: Color = ColorTokens.White,
    onSubtleAlt: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
    onSubtle = onSubtle,
    onSubtleAlt = onSubtleAlt,
)

public fun darkCriticalColors(
    normal: Color = Color(0xFFFF5050),
    normalAlt: Color = ColorTokens.RedDarkHover,
    subtle: Color = Color(0xFF4C323C),
    subtleAlt: Color = Color(0xFF664C56),
    strong: Color = ColorTokens.RedNormal,
    onNormal: Color = ColorTokens.White.invert(),
    onSubtle: Color = ColorTokens.White,
    onSubtleAlt: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    onNormal = onNormal,
    onSubtle = onSubtle,
    onSubtleAlt = onSubtleAlt,
)

public fun darkBundleColors(
    basic: Color = ColorTokens.BundleBasic.invert(),
    basicGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleBasicStart,
        ColorTokens.BundleBasicEnd,
    ),
    medium: Color = ColorTokens.BundleMedium.invert(),
    mediumGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleMediumStart,
        ColorTokens.BundleMediumEnd,
    ),
    top: Color = ColorTokens.InkNormal.invert(),
    topGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleTopStart,
        ColorTokens.BundleTopEnd,
    ),
    onBasic: Color = ColorTokens.White,
    onMedium: Color = ColorTokens.White,
    onTop: Color = ColorTokens.White,
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
