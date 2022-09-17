package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    subtle: Color = Color(0xFF151E29),
    subtleAlt: Color = Color(0xFF1F2B35),
    normal: Color = Color(0xFF293845),
    normalAlt: Color = Color(0xFF324454),
    strong: Color = Color(0xFF38414B),
    strongAlt: Color = Color(0xFF424D59),
    disabled: Color = Color(0xFF293845),
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

public fun darkContentColors(
    normal: Color = Color(0xFFFFFFFF),
    minor: Color = Color(0xFFBAC7D5),
    subtle: Color = Color(0xFFA1AEBC),
    highlight: Color = Color(0xFF00EECC),
    disabled: Color = Color(0xFF424D59),
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    highlight = highlight,
    disabled = disabled,
)

public fun darkPrimaryColors(
    normal: Color = Color(0xFF00CBAE),
    normalAlt: Color = Color(0xFF00E4C3),
    subtle: Color = Color(0xFF0A4038),
    subtleAlt: Color = Color(0xFF0F6558),
    strong: Color = Color(0xFF00EECC),
    strongAlt: Color = Color(0xFF09FFDB),
    onNormal: Color = Color.Black,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun darkInfoColors(
    normal: Color = Color(0xFF2AA0FE),
    normalAlt: Color = Color(0xFF43ABFE),
    subtle: Color = Color(0xFF21425F),
    subtleAlt: Color = Color(0xFF2A557B),
    strong: Color = Color(0xFF67BBFE),
    strongAlt: Color = Color(0xFF80C6FE),
    onNormal: Color = Color.Black,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun darkSuccessColors(
    normal: Color = Color(0xFF3BCE4E),
    normalAlt: Color = Color(0xFF4FD360),
    subtle: Color = Color(0xFF254B3C),
    subtleAlt: Color = Color(0xFF326551),
    strong: Color = Color(0xFF64D873),
    strongAlt: Color = Color(0xFF87DD85),
    onNormal: Color = Color.Black,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun darkWarningColors(
    normal: Color = Color(0xFFFBA132),
    normalAlt: Color = Color(0xFFFBAC4B),
    subtle: Color = Color(0xFF4B4236),
    subtleAlt: Color = Color(0xFF615545),
    strong: Color = Color(0xFFFCB35A),
    strongAlt: Color = Color(0xFFFCBE73),
    onNormal: Color = Color.Black,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun darkCriticalColors(
    normal: Color = Color(0xFFFF5050),
    normalAlt: Color = Color(0xFFFF6969),
    subtle: Color = Color(0xFF4C323C),
    subtleAlt: Color = Color(0xFF63414E),
    strong: Color = Color(0xFFFF7070),
    strongAlt: Color = Color(0xFFFE8989),
    onNormal: Color = Color.Black,
): FeatureColors = FeatureColors(
    normal = normal,
    normalAlt = normalAlt,
    subtle = subtle,
    subtleAlt = subtleAlt,
    strong = strong,
    strongAlt = strongAlt,
    onNormal = onNormal,
)

public fun darkBundleColors(
    basic: Color = ColorTokens.BundleBasic,
    basicGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleBasicStart,
        ColorTokens.BundleBasicEnd,
    ),
    medium: Color = ColorTokens.BundleMedium,
    mediumGradient: Brush = bundleLinearGradient(
        ColorTokens.BundleMediumStart,
        ColorTokens.BundleMediumEnd,
    ),
    top: Color = ColorTokens.InkDark,
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
