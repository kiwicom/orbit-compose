package kiwi.orbit.foundation

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.material.Shapes as MaterialShapes

@Immutable
public data class Shapes(
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val normal: CornerBasedShape = RoundedCornerShape(6.dp),
    val large: CornerBasedShape = RoundedCornerShape(12.dp),
) {
    internal fun toMaterialShapes(): MaterialShapes {
        return MaterialShapes(
            small = small,
            medium = normal,
            large = large,
        )
    }
}

internal val LocalShapes = staticCompositionLocalOf { Shapes() }
