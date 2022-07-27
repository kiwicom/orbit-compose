package kiwi.orbit.compose.catalog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CatalogApplication() {
    val systemUiController = rememberSystemUiController()

    var isLightTheme by rememberSaveable { mutableStateOf<Boolean?>(null) }
    val isLightThemeFinal = isLightTheme ?: !isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = isLightThemeFinal,
        )
    }

    AppTheme(isLightTheme = isLightThemeFinal) {
        NavGraph(
            onToggleTheme = {
                isLightTheme = !isLightThemeFinal
            },
        )
    }
}
