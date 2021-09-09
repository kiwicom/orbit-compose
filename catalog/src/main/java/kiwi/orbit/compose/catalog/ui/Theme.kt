package kiwi.orbit.compose.catalog.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
fun AppTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    LocalContentEmphasis.current

    val context = LocalContext.current
    val fontFamily = remember { createFontFamily(context) }
    OrbitTheme(
        typography = Typography(defaultFontFamily = fontFamily),
        colors = if (isLightTheme) lightColors() else darkColors(),
    ) {
        content()
    }
}

private fun createFontFamily(context: Context): FontFamily {
    val resources = context.resources
    val packageName = context.packageName
    val book = resources.getIdentifier("circular_pro_book", "font", packageName)
    val medium = resources.getIdentifier("circular_pro_medium", "font", packageName)
    val bold = resources.getIdentifier("circular_pro_bold", "font", packageName)

    if (book == 0 || medium == 0 || bold == 0) {
        return FontFamily.Default
    }

    return FontFamily(
        Font(book, FontWeight.Normal),
        Font(medium, FontWeight.Medium),
        Font(bold, FontWeight.Bold),
    )
}
