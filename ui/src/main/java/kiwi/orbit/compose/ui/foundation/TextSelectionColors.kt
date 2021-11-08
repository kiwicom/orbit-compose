package kiwi.orbit.compose.ui.foundation

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
internal fun rememberTextSelectionColors(colors: Colors): TextSelectionColors =
    remember(colors.interactive.main, colors.interactive.subtleAlt) {
        TextSelectionColors(
            handleColor = colors.interactive.main,
            backgroundColor = colors.interactive.subtleAlt,
        )
    }
