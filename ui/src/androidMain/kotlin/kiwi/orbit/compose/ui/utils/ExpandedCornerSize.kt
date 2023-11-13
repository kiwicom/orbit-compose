package kiwi.orbit.compose.ui.utils

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

internal class ExpandedCornerSize(
    private val original: CornerSize,
    private val extraSize: Dp,
) : CornerSize {
    override fun toPx(shapeSize: Size, density: Density): Float {
        val originalSize = original.toPx(shapeSize, density)
        if (originalSize == 0f) return originalSize
        return originalSize + with(density) { extraSize.toPx() }
    }
}
