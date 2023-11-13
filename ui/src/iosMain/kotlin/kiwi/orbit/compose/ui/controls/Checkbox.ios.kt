package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kiwi.orbit.compose.icons.Icons

@Composable
internal actual fun getCheckPainter(checked: Boolean): Painter =
    Icons.Check
