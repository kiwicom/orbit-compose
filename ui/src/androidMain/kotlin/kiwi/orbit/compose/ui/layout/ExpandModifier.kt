package kiwi.orbit.compose.ui.layout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.offset

/**
 * Expands drawing beyond the assigned rect on all sides by provided [all] size.
 *
 * This modifier may be utilized to workaround (shadow)cropping issues. Apply the same
 * [all] shift to the inner content as a padding.
 *
 * ```
 * AnimatedContent(Modifier.expand(16.dp)) {
 *   CustomContent(Modifier.padding(16.dp))
 * }
 * ```
 */
public fun Modifier.expand(all: Dp): Modifier =
    this then ExpandModifierNodeElement(all, all)

/**
 * Expands drawing beyond the assigned rect on all sides by provided [horizontal] and [vertical] size.
 *
 * This modifier may be utilized to workaround (shadow)cropping issues. Apply the same
 * [horizontal] and [vertical] shift to the inner content as a padding.
 *
 * ```
 * AnimatedContent(Modifier.expand(horizontal = 16.dp, vertical = 8.dp)) {
 *   CustomContent(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
 * }
 * ```
 */
public fun Modifier.expand(horizontal: Dp, vertical: Dp): Modifier =
    this then ExpandModifierNodeElement(horizontal, vertical)

private data class ExpandModifierNodeElement(
    val horizontal: Dp,
    val vertical: Dp,
) : ModifierNodeElement<ExpandModifierNode>() {
    override fun InspectorInfo.inspectableProperties() {
        name = "expand"
        properties["horizontal"] = horizontal
        properties["vertical"] = vertical
    }

    override fun create(): ExpandModifierNode = ExpandModifierNode(horizontal, vertical)

    override fun update(node: ExpandModifierNode) {
        node.horizontal = horizontal
        node.vertical = vertical
    }
}

private class ExpandModifierNode(
    var horizontal: Dp,
    var vertical: Dp,
) : Modifier.Node(), LayoutModifierNode {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val shiftXPx = horizontal.roundToPx()
        val shiftYPx = vertical.roundToPx()
        val expandedConstraints = constraints.offset(
            horizontal = shiftXPx * 2,
            vertical = shiftYPx * 2,
        )
        val placeable = measurable.measure(expandedConstraints)
        val width = constraints.constrainWidth(placeable.width - shiftXPx * 2)
        val height = constraints.constrainHeight(placeable.height - shiftYPx * 2)

        return layout(width, height) {
            placeable.place(-shiftXPx, -shiftYPx)
        }
    }
}
