package kiwi.orbit.compose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CatalogApplication() {
    ProvideWindowInsets {
        var isLightTheme by remember { mutableStateOf(true) }
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = isLightTheme
            )
            systemUiController.setNavigationBarColor(
                color = when (isLightTheme) {
                    true -> Color(0xE3FFFFFF) // semi-transparent white
                    false -> Color(0xC3000000) // semi-transparent black
                },
            )
        }

        AppTheme(isLightTheme = isLightTheme) {
            NavGraph(
                onToggleTheme = {
                    isLightTheme = !isLightTheme
                }
            )
        }
    }
}
