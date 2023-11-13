package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle

internal actual fun createDefaultPlatformTextStyle(): PlatformTextStyle =
    PlatformTextStyle(PlatformSpanStyle(), PlatformParagraphStyle())
