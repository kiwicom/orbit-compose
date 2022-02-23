package kiwi.orbit.compose.ui.layout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.offset

/**
 * Expands drawing beyond the assigned rect on all sides by provided [shift] size.
 *
 * This modifier may be utilized to workaround (shadow)cropping issues. Apply the same
 * [shift] to the inner content as a padding.
 *
 * ```
 * AnimatedContent(Modifier.expand(16.dp)) {
 *   CustomContent(Modifier.padding(16.dp))
 * }
 * ```
 */
public fun Modifier.expand(shift: Dp): Modifier =
    then(ExpandModifier(shift))

private data class ExpandModifier(
    private val shift: Dp,
) : LayoutModifier {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val shiftPx = shift.roundToPx()
        val expandedConstraints = constraints.offset(
            horizontal = shiftPx * 2,
            vertical = shiftPx * 2,
        )
        val placeable = measurable.measure(expandedConstraints)
        val width = placeable.width - shiftPx * 2
        val height = placeable.height - shiftPx * 2

        return layout(width, height) {
            placeable.place(-shiftPx, -shiftPx)
        }
    }
}
