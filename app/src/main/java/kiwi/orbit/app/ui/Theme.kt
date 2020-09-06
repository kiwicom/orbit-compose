package kiwi.orbit.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.R
import kiwi.orbit.foundation.Typography

val fonts = fontFamily(
    font(R.font.circular_pro_book, FontWeight.Normal),
    font(R.font.circular_pro_medium, FontWeight.Medium),
    font(R.font.circular_pro_bold, FontWeight.Bold),
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    OrbitTheme(
        typography = Typography(defaultFontFamily = fonts),
    ) {
        content()
    }
}
