package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

public fun lightColors(
    surface: SurfaceColors = lightSurfaceColors(),
    content: ContentColors = lightContentColors(),
    primary: FeatureColors = lightPrimaryColors(),
    info: FeatureColors = lightInfoColors(),
    success: FeatureColors = lightSuccessColors(),
    warning: FeatureColors = lightWarningColors(),
    critical: FeatureColors = lightCriticalColors(),
    bundle: BundleColors = lightBundleColors(),
): Colors = Colors(
    surface = surface,
    content = content,
    primary = primary,
    info = info,
    success = success,
    warning = warning,
    critical = critical,
    bundle = bundle,
    isLight = true,
)

public fun lightSurfaceColors(
    main: Color = ColorTokens.White,
    background: Color = ColorTokens.CloudLight,
    subtle: Color = ColorTokens.CloudNormal,
    strong: Color = ColorTokens.CloudDark,
    disabled: Color = ColorTokens.CloudDarker,
): SurfaceColors = SurfaceColors(
    main = main,
    background = background,
    subtle = subtle,
    strong = strong,
    disabled = disabled,
)

public fun lightContentColors(
    normal: Color = ColorTokens.InkNormal,
    minor: Color = ColorTokens.InkLight,
    subtle: Color = ColorTokens.InkLighter,
    highlight: Color = ColorTokens.ProductDark,
    disabled: Color = ColorTokens.CloudDarkerHover,
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    highlight = highlight,
    disabled = disabled,
)

public fun lightPrimaryColors(
    normal: Color = ColorTokens.ProductNormal,
    normalAlt: Color = ColorTokens.ProductNormalHover,
    subtle: Color = ColorTokens.ProductLight,
    subtleAlt: Color = ColorTokens.ProductLightHover,
    strong: Color = ColorTokens.ProductDark,
    onNormal: Color = ColorTokens.White,
    onSubtle: Color = ColorTokens.ProductDark,
    onSubtleAlt: Color = ColorTokens.ProductDarkHover,
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

public fun lightInfoColors(
    normal: Color = ColorTokens.BlueNormal,
    normalAlt: Color = ColorTokens.BlueNormalHover,
    subtle: Color = ColorTokens.BlueLight,
    subtleAlt: Color = ColorTokens.BlueLightHover,
    strong: Color = ColorTokens.BlueDark,
    onNormal: Color = ColorTokens.White,
    onSubtle: Color = ColorTokens.BlueDark,
    onSubtleAlt: Color = ColorTokens.BlueDarkHover,
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

public fun lightSuccessColors(
    normal: Color = ColorTokens.GreenNormal,
    normalAlt: Color = ColorTokens.GreenNormalHover,
    subtle: Color = ColorTokens.GreenLight,
    subtleAlt: Color = ColorTokens.GreenLightHover,
    strong: Color = ColorTokens.GreenDark,
    onNormal: Color = ColorTokens.White,
    onSubtle: Color = ColorTokens.GreenDark,
    onSubtleAlt: Color = ColorTokens.GreenDarkHover,
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

public fun lightWarningColors(
    normal: Color = ColorTokens.OrangeNormal,
    normalAlt: Color = ColorTokens.OrangeNormalHover,
    subtle: Color = ColorTokens.OrangeLight,
    subtleAlt: Color = ColorTokens.OrangeLightHover,
    strong: Color = ColorTokens.OrangeDark,
    onNormal: Color = ColorTokens.White,
    onSubtle: Color = ColorTokens.OrangeDark,
    onSubtleAlt: Color = ColorTokens.OrangeDarkHover,
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

public fun lightCriticalColors(
    normal: Color = ColorTokens.RedNormal,
    normalAlt: Color = ColorTokens.RedNormalHover,
    subtle: Color = ColorTokens.RedLight,
    subtleAlt: Color = ColorTokens.RedLightHover,
    strong: Color = ColorTokens.RedDark,
    onNormal: Color = ColorTokens.White,
    onSubtle: Color = ColorTokens.RedDark,
    onSubtleAlt: Color = ColorTokens.RedDarkHover,
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

public fun lightBundleColors(
    basic: Color = ColorTokens.BundleBasic,
    basicGradient: Brush = bundleLinearGradient(ColorTokens.BundleBasicStart, ColorTokens.BundleBasicEnd),
    medium: Color = ColorTokens.BundleMedium,
    mediumGradient: Brush = bundleLinearGradient(ColorTokens.BundleMediumStart, ColorTokens.BundleMediumEnd),
    top: Color = ColorTokens.InkNormal,
    topGradient: Brush = bundleLinearGradient(ColorTokens.BundleTopStart, ColorTokens.BundleTopEnd),
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
