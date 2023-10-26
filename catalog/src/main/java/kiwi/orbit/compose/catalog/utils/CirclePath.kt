package kiwi.orbit.compose.catalog.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.sqrt

class CirclePath(
    private val progress: Float,
    private val origin: Offset = Offset(0f, 0f),
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = sqrt(
            size.height * size.height + size.width * size.width,
        ) * .5f * progress

        return Outline.Generic(
            Path().apply {
                addOval(
                    Rect(
                        center = center,
                        radius = radius,
                    ),
                )
            },
        )
    }
}
