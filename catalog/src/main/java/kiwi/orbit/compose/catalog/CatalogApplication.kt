package kiwi.orbit.compose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost

var isLightTheme by mutableStateOf(true)

@Composable
fun CatalogApplication() {
    ProvideWindowInsets {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = if (isLightTheme) Color(0x70FFFFFF) else Color(0x70000000),
            )
        }

        AppTheme(isLightTheme = isLightTheme) {
            DestinationsNavHost()
        }
    }
}
