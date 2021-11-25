package kiwi.orbit.compose.ui.controls.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints

/**
 * Simple Column implementation with Box's propagateMinConstraints=true behavior.
 */
@Composable
internal fun ColumnWithMinConstraints(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            content()
        },
        measurePolicy = remember {
            ColumnWithMinConstraintsMeasurePolicy()
        },
    )
}

private class ColumnWithMinConstraintsMeasurePolicy : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        var occupiedSpaceVertically = 0
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(
                constraints.copy(
                    maxHeight = if (constraints.maxHeight == Constraints.Infinity) {
                        Constraints.Infinity
                    } else {
                        constraints.maxHeight - occupiedSpaceVertically
                    }
                )
            )
            occupiedSpaceVertically += placeable.height
            placeable
        }

        val width = placeables.maxOf { it.width }.coerceAtLeast(constraints.minWidth)
        val height = occupiedSpaceVertically.coerceAtLeast(constraints.minHeight)

        return layout(width, height) {
            var offsetY = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(
                    x = 0,
                    y = offsetY,
                )
                offsetY += placeable.height
            }
        }
    }
}
