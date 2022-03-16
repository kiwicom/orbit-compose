package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    public constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
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
            // TODO: textAllCaps
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
    ) : this(
        title1 = title1.withDefaultFontFamily(defaultFontFamily),
        title2 = title2.withDefaultFontFamily(defaultFontFamily),
        title3 = title3.withDefaultFontFamily(defaultFontFamily),
        title4 = title4.withDefaultFontFamily(defaultFontFamily),
        title5 = title5.withDefaultFontFamily(defaultFontFamily),
        title6 = title6.withDefaultFontFamily(defaultFontFamily),
        bodyExtraLarge = bodyExtraLarge.withDefaultFontFamily(defaultFontFamily),
        bodyLarge = bodyLarge.withDefaultFontFamily(defaultFontFamily),
        bodyNormal = bodyNormal.withDefaultFontFamily(defaultFontFamily),
        bodySmall = bodySmall.withDefaultFontFamily(defaultFontFamily),
    )

    /**
     * Orbit BodyExtraLarge with Medium font-weight.
     */
    val bodyExtraLargeMedium: TextStyle
        get() = bodyExtraLarge.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyExtraLarge with Bold font-weight.
     */
    val bodyExtraLargeBold: TextStyle
        get() = bodyExtraLarge.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodyLarge with Medium font-weight.
     */
    val bodyLargeMedium: TextStyle
        get() = bodyLarge.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyLarge with Bold font-weight.
     */
    val bodyLargeBold: TextStyle
        get() = bodyLarge.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodyNormal with Medium font-weight.
     */
    val bodyNormalMedium: TextStyle
        get() = bodyNormal.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodyNormal with Bold font-weight.
     */
    val bodyNormalBold: TextStyle
        get() = bodyNormal.copy(fontWeight = FontWeight.Bold)

    /**
     * Orbit BodySmall with Medium font-weight.
     */
    val bodySmallMedium: TextStyle
        get() = bodySmall.copy(fontWeight = FontWeight.Medium)

    /**
     * Orbit BodySmall with Bold font-weight.
     */
    val bodySmallBold: TextStyle
        get() = bodySmall.copy(fontWeight = FontWeight.Bold)

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

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography: ProvidableCompositionLocal<Typography> =
    staticCompositionLocalOf { Typography() }
