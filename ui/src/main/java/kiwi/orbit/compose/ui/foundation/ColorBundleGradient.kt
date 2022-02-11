package kiwi.orbit.compose.ui.foundation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

internal fun bundleLinearGradient(
    start: Color,
    end: Color,
): Brush = Brush.linearGradient(
    colors = listOf(start, end),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f),
)
