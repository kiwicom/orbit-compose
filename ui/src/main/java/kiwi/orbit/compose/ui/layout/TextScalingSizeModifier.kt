package kiwi.orbit.compose.ui.layout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit

public fun Modifier.size(size: TextUnit): Modifier = then(
    TextScalingSizeModifier(
        width = size,
        height = size,
    ),
)

public fun Modifier.size(width: TextUnit, height: TextUnit): Modifier = then(
    TextScalingSizeModifier(
        width = width,
        height = height,
    ),
)

private data class TextScalingSizeModifier(
    private val width: TextUnit,
    private val height: TextUnit,
) : LayoutModifier {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val newConstraints = Constraints.fixed(width.roundToPx(), height.roundToPx())
        val placeable = measurable.measure(newConstraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}
