package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalElevationEnabled: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { true }
