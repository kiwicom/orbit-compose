package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

internal actual fun Modifier.sliderSystemGestureExclusion(enabled: Boolean): Modifier {
    if (!enabled) return this

    return composed {
        val density = LocalDensity.current
        val padding = with(density) { 16.dp.toPx() }
        systemGestureExclusion { coordinates ->
            Rect(
                -padding,
                -padding,
                coordinates.size.width + padding,
                coordinates.size.height.toFloat() + padding,
            )
        }
    }
}
