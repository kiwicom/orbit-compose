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
import kiwi.orbit.compose.ui.foundation.createTypography
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
fun AppTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    OrbitTheme(
        typography = rememberTypography(LocalContext.current),
        colors = when (isLightTheme) {
            true -> lightColors()
            false -> darkColors()
        },
        content = content,
    )
}

@OptIn(ExperimentalTextApi::class)
@SuppressLint("DiscouragedApi")
@Composable
private fun rememberTypography(context: Context): Typography = remember {
    val resources = context.resources
    val packageName = context.packageName

    val book = resources.getIdentifier("circular_pro_book", "font", packageName)
    val medium = resources.getIdentifier("circular_pro_medium", "font", packageName)
    val bold = resources.getIdentifier("circular_pro_bold", "font", packageName)

    if (book == 0 || medium == 0 || bold == 0) {
        return@remember createTypography()
    }

    return@remember createTypography(
        defaultFontFamily = FontFamily(
            Font(book, FontWeight.Normal),
            Font(medium, FontWeight.Medium),
            Font(bold, FontWeight.Bold),
        ),
        defaultLineHeightStyle = LineHeightStyle(
            // Accommodate for Circular Pro to 0.6f instead of Center
            LineHeightStyle.Alignment(0.6f),
            LineHeightStyle.Trim.None,
        ),
    )
}
