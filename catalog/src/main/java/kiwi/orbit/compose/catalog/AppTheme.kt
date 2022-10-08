package kiwi.orbit.compose.catalog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
fun AppTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val (fontFamily, lineHeightStyle) = remember { createFontFamily(context) }
    OrbitTheme(
        typography = Typography(fontFamily, lineHeightStyle),
        colors = when (isLightTheme) {
            true -> lightColors()
            false -> darkColors()
        },
    ) {
        content()
    }
}

@OptIn(ExperimentalTextApi::class)
@SuppressLint("DiscouragedApi")
private fun createFontFamily(context: Context): Pair<FontFamily, LineHeightStyle.Alignment> {
    val resources = context.resources
    val packageName = context.packageName

    val book = resources.getIdentifier("circular_pro_book", "font", packageName)
    val medium = resources.getIdentifier("circular_pro_medium", "font", packageName)
    val bold = resources.getIdentifier("circular_pro_bold", "font", packageName)

    if (book == 0 || medium == 0 || bold == 0) {
        return FontFamily.Default to LineHeightStyle.Alignment.Center
    }

    return Pair(
        FontFamily(
            Font(book, FontWeight.Normal),
            Font(medium, FontWeight.Medium),
            Font(bold, FontWeight.Bold),
        ),
        // Accommodate for Circular Pro to 0.6f instead of Center
        LineHeightStyle.Alignment(0.6f),
    )
}
