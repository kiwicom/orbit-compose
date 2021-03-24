package kiwi.orbit.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.LocalSystemUiController
import com.google.accompanist.systemuicontroller.rememberAndroidSystemUiController
import kiwi.orbit.app.ui.AppTheme

@Composable
fun DemoApp() {
    ProvideWindowInsets {
        val systemUiController = rememberAndroidSystemUiController()
        CompositionLocalProvider(LocalSystemUiController provides systemUiController) {
            var isLightTheme by remember { mutableStateOf(true) }

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = isLightTheme,
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
}
