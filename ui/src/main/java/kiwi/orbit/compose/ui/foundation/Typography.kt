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
    val displayTitle: TextStyle,
    val displaySubtitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title4: TextStyle,
    val title5: TextStyle,
    val bodyLarge: TextStyle,
    val bodyNormal: TextStyle,
    val bodySmall: TextStyle,
) {
    public constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
        displayTitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        ),
        displaySubtitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
        ),
        title1: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
        ),
        title2: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
        ),
        title3: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        title4: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        title5: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            // TODO: textAllCaps
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
        displayTitle = displayTitle.withDefaultFontFamily(defaultFontFamily),
        displaySubtitle = displaySubtitle.withDefaultFontFamily(defaultFontFamily),
        title1 = title1.withDefaultFontFamily(defaultFontFamily),
        title2 = title2.withDefaultFontFamily(defaultFontFamily),
        title3 = title3.withDefaultFontFamily(defaultFontFamily),
        title4 = title4.withDefaultFontFamily(defaultFontFamily),
        title5 = title5.withDefaultFontFamily(defaultFontFamily),
        bodyLarge = bodyLarge.withDefaultFontFamily(defaultFontFamily),
        bodyNormal = bodyNormal.withDefaultFontFamily(defaultFontFamily),
        bodySmall = bodySmall.withDefaultFontFamily(defaultFontFamily),
    )
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography: ProvidableCompositionLocal<Typography> = staticCompositionLocalOf { Typography() }
