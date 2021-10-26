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
fun DemoApp() {
    ProvideWindowInsets {
        var isLightTheme by remember { mutableStateOf(true) }
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = if (isLightTheme) Color(0x70FFFFFF) else Color(0x70000000),
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
