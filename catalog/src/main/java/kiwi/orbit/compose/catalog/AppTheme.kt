package kiwi.orbit.compose.catalog

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import java.lang.reflect.Constructor
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@OptIn(ExperimentalTextApi::class)
@Composable
fun AppTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val (fontFamily, lineHeightStyle) = remember { createFontFamily(context) }
    OrbitTheme(
        typography = Typography(fontFamily, lineHeightStyle),
        colors = if (isLightTheme) lightColors() else darkColors(),
    ) {
        content()
    }
}

@ExperimentalTextApi
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
        /**
         * Circular Pro has weirdly broken ascent/descent font metrics causing wrong vertical alignment.
         * Manually shifting the font a bit lower.
         */
        LineHeightStyleAlignmentConstructor.newInstance(60)
    )
}

@OptIn(ExperimentalTextApi::class)
private val LineHeightStyleAlignmentConstructor: Constructor<LineHeightStyle.Alignment> by lazy {
    val classDefinition = LineHeightStyle.Alignment::class.java
    val constructor = classDefinition.getDeclaredConstructor(Int::class.java)
    constructor.isAccessible = true
    constructor
}
