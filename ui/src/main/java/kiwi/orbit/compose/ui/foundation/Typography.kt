package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

// TODO: remove data class to allow maintain better binary compatibility
@Immutable
public data class Typography internal constructor(
    /**
     * Orbit Title1 text style.
     */
    val title1: TextStyle,

    /**
     * Orbit Title2 text style.
     */
    val title2: TextStyle,

    /**
     * Orbit Title3 text style.
     */
    val title3: TextStyle,

    /**
     * Orbit Title4 text style.
     */
    val title4: TextStyle,

    /**
     * Orbit Title5 text style.
     */
    val title5: TextStyle,

    /**
     * Orbit Title6 text style.
     *
     * !!! Compose does not support UPPERCASE transform in [TextStyle], apply it manually
     * via [String.uppercase] function.
     */
    val title6: TextStyle,

    /**
     * Orbit BodyExtraLarge with Normal font-weight.
     */
    val bodyExtraLarge: TextStyle,

    /**
     * Orbit BodyLarge with Normal font-weight.
     */
    val bodyLarge: TextStyle,

    /**
     * Orbit BodyNormal with normal font-weight.
     */
    val bodyNormal: TextStyle,

    /**
     * Orbit BodySmall with normal font-weight.
     */
    val bodySmall: TextStyle,
) {
    /**
     * Orbit BodyExtraLarge with Medium font-weight.
     */
    val bodyExtraLargeMedium: TextStyle = bodyExtraLarge.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyExtraLarge with Bold font-weight.
     */
    val bodyExtraLargeBold: TextStyle = bodyExtraLarge.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodyLarge with Medium font-weight.
     */
    val bodyLargeMedium: TextStyle = bodyLarge.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyLarge with Bold font-weight.
     */
    val bodyLargeBold: TextStyle = bodyLarge.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodyNormal with Medium font-weight.
     */
    val bodyNormalMedium: TextStyle = bodyNormal.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyNormal with Bold font-weight.
     */
    val bodyNormalBold: TextStyle = bodyNormal.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodySmall with Medium font-weight.
     */
    val bodySmallMedium: TextStyle = bodySmall.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodySmall with Bold font-weight.
     */
    val bodySmallBold: TextStyle = bodySmall.copy(fontWeight = FontWeight.Bold)

    @Suppress("Dependency")
    internal fun toMaterialTypography(): androidx.compose.material.Typography =
        androidx.compose.material.Typography(
            defaultFontFamily = title1.fontFamily ?: FontFamily.Default,
            h1 = title1.copy(fontSize = 96.sp),
            h2 = title1.copy(fontSize = 60.sp),
            h3 = title1.copy(fontSize = 38.sp),
            h4 = title1,
            h5 = title2,
            h6 = title3,
            subtitle1 = title4,
            subtitle2 = title5,
            overline = title6,
            body1 = bodyNormal,
            body2 = bodySmall,
            button = title5,
        )
}

@Suppress("DEPRECATION")
public fun createTypography(
    defaultFontFamily: FontFamily = FontFamily.Default,
    defaultPlatformTextStyle: PlatformTextStyle? = PlatformTextStyle(includeFontPadding = false),
    defaultLineHeightStyle: LineHeightStyle? = LineHeightStyle(
        LineHeightStyle.Alignment.Center,
        LineHeightStyle.Trim.None,
    ),
    title1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.sp,
    ),
    title2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    title3: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    title4: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    title5: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    title6: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    bodyExtraLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodyLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    bodyNormal: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
): Typography = Typography(
    title1 = title1.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    title2 = title2.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    title3 = title3.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    title4 = title4.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    title5 = title5.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    title6 = title6.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    bodyExtraLarge = bodyExtraLarge.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    bodyLarge = bodyLarge.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    bodyNormal = bodyNormal.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
    bodySmall = bodySmall.with(defaultFontFamily, defaultPlatformTextStyle, defaultLineHeightStyle),
)

private fun TextStyle.with(
    fontFamily: FontFamily,
    platformTextStyle: PlatformTextStyle?,
    lineHeightStyle: LineHeightStyle?,
): TextStyle = copy(
    fontFamily = this.fontFamily ?: fontFamily,
    platformStyle = this.platformStyle ?: platformTextStyle,
    lineHeightStyle = this.lineHeightStyle ?: lineHeightStyle,
)

internal val LocalTypography: ProvidableCompositionLocal<Typography> =
    staticCompositionLocalOf { createTypography() }
