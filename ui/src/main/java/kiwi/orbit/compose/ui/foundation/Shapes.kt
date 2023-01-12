package kiwi.orbit.compose.ui.foundation

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
public data class Shapes(
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val normal: CornerBasedShape = RoundedCornerShape(6.dp),
    val large: CornerBasedShape = RoundedCornerShape(12.dp),
) {
    @Suppress("Dependency")
    internal fun toMaterial3Shapes(): androidx.compose.material3.Shapes =
        androidx.compose.material3.Shapes(
            small = small,
            medium = normal,
            large = large,
        )
}

internal val LocalShapes: ProvidableCompositionLocal<Shapes> = staticCompositionLocalOf { Shapes() }
