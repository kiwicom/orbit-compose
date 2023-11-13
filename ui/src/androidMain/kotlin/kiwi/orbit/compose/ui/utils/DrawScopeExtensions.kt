package kiwi.orbit.compose.ui.utils

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.max

/**
 * Draws round rect, but the redius is defined by the outer radius of the stroke.
 */
internal fun DrawScope.drawStrokeOutlineRoundRect(
    color: Color,
    topLeft: Offset,
    size: Size,
    cornerRadius: CornerRadius,
    stroke: Stroke,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) {
    // Stroked rounded rect with the corner radius
    // shrunk by half of the stroke width. This will ensure that the
    // outer curvature of the rounded rectangle will have the desired
    // corner radius.
    drawRoundRect(
        color = color,
        topLeft = topLeft + Offset(stroke.width / 2f, stroke.width / 2f),
        size = Size(size.width - stroke.width, size.height - stroke.width),
        cornerRadius = cornerRadius.shrink(stroke.width / 2f),
        alpha = alpha,
        style = stroke,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}

private fun CornerRadius.shrink(value: Float): CornerRadius = CornerRadius(
    max(0f, this.x - value),
    max(0f, this.y - value),
)
