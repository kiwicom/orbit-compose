package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.text.PlatformTextStyle

internal actual fun createDefaultPlatformTextStyle(): PlatformTextStyle =
    PlatformTextStyle(includeFontPadding = false)
