package kiwi.orbit.compose.ui.controls.field

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun FieldContent(
    fieldContent: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    onLeadingIconClick: (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    onTrailingIconClick: (() -> Unit)?,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
) {
    val measurePolicy = remember(singleLine) {
        FieldContentMeasurePolicy(singleLine)
    }
    Layout(
        content = {
            FieldIcon(
                onClick = onLeadingIconClick,
                icon = leadingIcon,
                leading = true,
                modifier = Modifier.layoutId(LeadingId),
            )
            FieldIcon(
                onClick = onTrailingIconClick,
                icon = trailingIcon,
                leading = false,
                modifier = Modifier.layoutId(TrailingId),
            )
            if (placeholder != null) {
                Box(
                    Modifier.layoutId(PlaceholderId),
                ) {
                    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                        ProvideContentEmphasis(ContentEmphasis.Subtle, content = placeholder)
                    }
                }
            }
            Box(
                modifier = Modifier.layoutId(FieldId),
                propagateMinConstraints = true,
            ) {
                fieldContent()
            }
        },
        modifier = modifier,
        measurePolicy = measurePolicy,
    )
}

private class FieldContentMeasurePolicy(
    val singleLine: Boolean,
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        val verticalPadding = FieldPadding.roundToPx()
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        var occupiedSpaceHorizontally = 0

        val leadingPlaceable = measurables.first { it.layoutId == LeadingId }.measure(looseConstraints)
        occupiedSpaceHorizontally += leadingPlaceable.width

        val trailingPlaceable = measurables.first { it.layoutId == TrailingId }
            .measure(looseConstraints.offset(horizontal = -occupiedSpaceHorizontally))
        occupiedSpaceHorizontally += trailingPlaceable.width

        val fieldConstraints = constraints
            .copy(minHeight = 0)
            .offset(
                vertical = -verticalPadding * 2,
                horizontal = -occupiedSpaceHorizontally,
            )
        val fieldPlaceable = measurables.first { it.layoutId == FieldId }.measure(fieldConstraints)

        val placeholderConstraints = fieldConstraints.copy(minWidth = 0)
        val placeholderPlaceable = measurables.find { it.layoutId == PlaceholderId }
            ?.measure(placeholderConstraints)

        val width = calculateWidth(
            fieldWidth = fieldPlaceable.width,
            leadingWidth = leadingPlaceable.width,
            trailingWidth = trailingPlaceable.width,
            placeholderWidth = placeholderPlaceable?.width ?: 0,
            constraints = constraints,
        )
        val height = calculateHeight(
            fieldHeight = fieldPlaceable.height,
            leadingHeight = leadingPlaceable.height,
            trailingHeight = trailingPlaceable.height,
            placeholderHeight = placeholderPlaceable?.height ?: 0,
            constraints = constraints,
            density = density,
        )

        return layout(width, height) {
            val verticalPaddingPx = FieldPadding.roundToPx()

            leadingPlaceable.placeRelative(
                x = 0,
                y = if (singleLine) {
                    Alignment.CenterVertically.align(leadingPlaceable.height, height)
                } else {
                    verticalPaddingPx
                },
            )
            trailingPlaceable.placeRelative(
                x = width - trailingPlaceable.width,
                y = if (singleLine) {
                    Alignment.CenterVertically.align(trailingPlaceable.height, height)
                } else {
                    verticalPaddingPx
                },
            )

            // Single line text field without label places its input center vertically. Multiline text
            // field without label places its input at the top with padding
            val fieldVerticalPosition = if (singleLine) {
                Alignment.CenterVertically.align(fieldPlaceable.height, height)
            } else {
                verticalPaddingPx
            }
            fieldPlaceable.placeRelative(
                x = leadingPlaceable.width,
                y = fieldVerticalPosition,
                zIndex = 1f,
            )

            // placeholder is placed similar to the text input above
            if (placeholderPlaceable != null) {
                val placeholderVerticalPosition = if (singleLine) {
                    Alignment.CenterVertically.align(placeholderPlaceable.height, height)
                } else {
                    verticalPaddingPx
                }
                placeholderPlaceable.placeRelative(
                    x = leadingPlaceable.width,
                    y = placeholderVerticalPosition,
                )
            }
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int {
        return intrinsicHeight(measurables, width, IntrinsicMeasurable::maxIntrinsicHeight)
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int {
        return intrinsicHeight(measurables, width, IntrinsicMeasurable::minIntrinsicHeight)
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int {
        return intrinsicWidth(measurables, height, IntrinsicMeasurable::maxIntrinsicWidth)
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int {
        return intrinsicWidth(measurables, height, IntrinsicMeasurable::minIntrinsicWidth)
    }

    private fun intrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
        intrinsicMeasurer: IntrinsicMeasurable.(Int) -> Int,
    ): Int {
        val fieldWidth = measurables.first { it.layoutId == FieldId }.intrinsicMeasurer(height)
        val leadingWidth = measurables.first { it.layoutId == LeadingId }.intrinsicMeasurer(height)
        val trailingWidth = measurables.first { it.layoutId == TrailingId }.intrinsicMeasurer(height)
        val placeholderWidth =
            measurables.find { it.layoutId == PlaceholderId }?.intrinsicMeasurer(height) ?: 0
        return calculateWidth(
            fieldWidth = fieldWidth,
            leadingWidth = leadingWidth,
            trailingWidth = trailingWidth,
            placeholderWidth = placeholderWidth,
            constraints = ZeroConstraints,
        )
    }

    private fun IntrinsicMeasureScope.intrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
        intrinsicMeasurer: IntrinsicMeasurable.(Int) -> Int,
    ): Int {
        val fieldHeight = measurables.first { it.layoutId == FieldId }.intrinsicMeasurer(width)
        val leadingHeight = measurables.first { it.layoutId == LeadingId }.intrinsicMeasurer(width)
        val trailingHeight = measurables.first { it.layoutId == TrailingId }.intrinsicMeasurer(width)
        val placeholderHeight =
            measurables.find { it.layoutId == PlaceholderId }?.intrinsicMeasurer(width) ?: 0
        return calculateHeight(
            fieldHeight = fieldHeight,
            leadingHeight = leadingHeight,
            trailingHeight = trailingHeight,
            placeholderHeight = placeholderHeight,
            constraints = ZeroConstraints,
            density = density,
        )
    }
}

private fun calculateWidth(
    fieldWidth: Int,
    leadingWidth: Int,
    trailingWidth: Int,
    placeholderWidth: Int,
    constraints: Constraints,
): Int {
    val middleSection = maxOf(fieldWidth, placeholderWidth)
    val wrappedWidth = leadingWidth + middleSection + trailingWidth
    return wrappedWidth.coerceAtLeast(constraints.minWidth)
}

private fun calculateHeight(
    fieldHeight: Int,
    leadingHeight: Int,
    trailingHeight: Int,
    placeholderHeight: Int,
    constraints: Constraints,
    density: Float,
): Int {
    val topBottomPadding = FieldPadding.value * density
    val middleSection = max(fieldHeight, placeholderHeight)
    val wrappedHeight = topBottomPadding * 2 + middleSection

    return maxOf(
        wrappedHeight.roundToInt(),
        max(leadingHeight, trailingHeight),
        constraints.minHeight,
    )
}

internal val IntrinsicMeasurable.layoutId: Any?
    get() = (parentData as? LayoutIdParentData)?.layoutId

private val ZeroConstraints = Constraints(0, 0, 0, 0)

private val FieldPadding = 12.dp

private const val FieldId = "Field"
private const val PlaceholderId = "Placeholder"
private const val LeadingId = "Leading"
private const val TrailingId = "Trailing"
