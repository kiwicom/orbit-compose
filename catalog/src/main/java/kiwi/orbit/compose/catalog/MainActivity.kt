package kiwi.orbit.compose.catalog

import android.app.UiModeManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CatalogApplication(this)
        }
    }

    internal fun setUiMode(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val uiModeManager = getSystemService<UiModeManager>()!!
            uiModeManager.setApplicationNightMode(
                if (isLight) UiModeManager.MODE_NIGHT_NO else UiModeManager.MODE_NIGHT_YES,
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                if (isLight) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES,
            )
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { !isLight },
            navigationBarStyle = SystemBarStyle.auto(DefaultLightScrim, DefaultDarkScrim) { !isLight },
        )
    }

    private val DefaultLightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
    private val DefaultDarkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
}
