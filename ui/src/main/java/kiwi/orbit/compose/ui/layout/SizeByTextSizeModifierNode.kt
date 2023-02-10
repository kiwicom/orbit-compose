package kiwi.orbit.compose.ui.layout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit

public fun Modifier.size(size: TextUnit): Modifier =
    this then SizeByTextSizeModifierElementNode(size, size)

public fun Modifier.size(width: TextUnit, height: TextUnit): Modifier =
    this then SizeByTextSizeModifierElementNode(width, height)

private data class SizeByTextSizeModifierElementNode(
    val width: TextUnit,
    val height: TextUnit,
) : ModifierNodeElement<SizeByTextSizeModifierNode>() {
    override fun InspectorInfo.inspectableProperties() {
        name = "sizeByTextSize"
        properties["width"] = width
        properties["height"] = height
    }

    override fun create() = SizeByTextSizeModifierNode(width, height)

    override fun update(node: SizeByTextSizeModifierNode) {
        node.width = width
        node.height = height
    }
}

private class SizeByTextSizeModifierNode(
    var width: TextUnit,
    var height: TextUnit,
) : Modifier.Node(), LayoutModifierNode {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val newConstraints = Constraints.fixed(width.roundToPx(), height.roundToPx())
        val placeable = measurable.measure(newConstraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}
