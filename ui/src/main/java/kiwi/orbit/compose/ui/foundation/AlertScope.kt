package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalAlertScope: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { true }
