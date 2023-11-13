package kiwi.orbit.compose.ui.controls.internal

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

internal fun Modifier.background(color: Color, brush: Brush?, shape: Shape): Modifier =
    when (brush) {
        null -> background(color, shape)
        else -> background(brush, shape)
    }
