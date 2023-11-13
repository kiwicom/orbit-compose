package kiwi.orbit.compose.ui.foundation

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
internal fun rememberTextSelectionColors(colors: Colors): TextSelectionColors =
    remember(colors.info.normal) {
        TextSelectionColors(
            handleColor = colors.info.normal,
            backgroundColor = colors.info.normal.copy(alpha = 0.2f),
        )
    }
