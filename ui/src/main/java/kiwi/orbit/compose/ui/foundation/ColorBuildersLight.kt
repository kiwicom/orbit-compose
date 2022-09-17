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
    subtle: Color = ColorTokens.CloudLight,
    subtleAlt: Color = ColorTokens.CloudLightHover,
    normal: Color = ColorTokens.CloudNormal,
    normalAlt: Color = ColorTokens.CloudNormalHover,
    strong: Color = ColorTokens.CloudDark,
    strongAlt: Color = ColorTokens.CloudDarkHover,
    disabled: Color = ColorTokens.CloudNormal,
): SurfaceColors = SurfaceColors(
    main = main,
    subtle = subtle,
    subtleAlt = subtleAlt,
    normal = normal,
    normalAlt = normalAlt,
    strong = strong,
    strongAlt = strongAlt,
    disabled = disabled,
)

public fun lightContentColors(
    normal: Color = ColorTokens.InkDark,
    minor: Color = ColorTokens.InkNormal,
    subtle: Color = ColorTokens.InkLight,
    highlight: Color = ColorTokens.ProductDark,
    disabled: Color = ColorTokens.CloudDarkHover,
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
    strongAlt: Color = ColorTokens.ProductDarkHover,
    onNormal: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun lightInfoColors(
    normal: Color = ColorTokens.BlueNormal,
    normalAlt: Color = ColorTokens.BlueNormalHover,
    subtle: Color = ColorTokens.BlueLight,
    subtleAlt: Color = ColorTokens.BlueLightHover,
    strong: Color = ColorTokens.BlueDark,
    strongAlt: Color = ColorTokens.BlueDarkHover,
    onNormal: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun lightSuccessColors(
    normal: Color = ColorTokens.GreenNormal,
    normalAlt: Color = ColorTokens.GreenNormalHover,
    subtle: Color = ColorTokens.GreenLight,
    subtleAlt: Color = ColorTokens.GreenLightHover,
    strong: Color = ColorTokens.GreenDark,
    strongAlt: Color = ColorTokens.GreenDarkHover,
    onNormal: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun lightWarningColors(
    normal: Color = ColorTokens.OrangeNormal,
    normalAlt: Color = ColorTokens.OrangeNormalHover,
    subtle: Color = ColorTokens.OrangeLight,
    subtleAlt: Color = ColorTokens.OrangeLightHover,
    strong: Color = ColorTokens.OrangeDark,
    strongAlt: Color = ColorTokens.OrangeDarkHover,
    onNormal: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun lightCriticalColors(
    normal: Color = ColorTokens.RedNormal,
    normalAlt: Color = ColorTokens.RedNormalHover,
    subtle: Color = ColorTokens.RedLight,
    subtleAlt: Color = ColorTokens.RedLightHover,
    strong: Color = ColorTokens.RedDark,
    strongAlt: Color = ColorTokens.RedDarkHover,
    onNormal: Color = ColorTokens.White,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun lightBundleColors(
    basic: Color = ColorTokens.BundleBasic,
    basicGradient: Brush = bundleLinearGradient(ColorTokens.BundleBasicStart, ColorTokens.BundleBasicEnd),
    medium: Color = ColorTokens.BundleMedium,
    mediumGradient: Brush = bundleLinearGradient(ColorTokens.BundleMediumStart, ColorTokens.BundleMediumEnd),
    top: Color = ColorTokens.InkDark,
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
