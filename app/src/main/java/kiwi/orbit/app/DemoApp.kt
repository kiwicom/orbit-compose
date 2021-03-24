package kiwi.orbit.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.accompanist.insets.ProvideWindowInsets
import kiwi.orbit.app.ui.AppTheme

@Composable
fun DemoApp() {
    ProvideWindowInsets {
        var isLightTheme by remember { mutableStateOf(true) }

        val view = LocalView.current
        val insetController = ViewCompat.getWindowInsetsController(view)
        insetController?.isAppearanceLightStatusBars = isLightTheme

        AppTheme(isLightTheme = isLightTheme) {
            NavGraph(
                onToggleTheme = {
                    isLightTheme = !isLightTheme
                }
            )
        }
    }
}
