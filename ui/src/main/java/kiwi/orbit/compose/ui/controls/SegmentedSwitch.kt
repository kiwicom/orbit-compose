package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

/**
 * A segmented switch displaying two options.
 *
 * Renders a segmented switch displaying [optionFirst] and [optionSecond].
 * Modify via custom passed [modifier].
 *
 * Example:
 *
 * ```
 * var selectedIndex by remember { mutableStateOf<Int?>(null) }
 * SegmentedSwitch(
 *   optionFirst = { Text("Off") },
 *   optionSecond = { Text("On") },
 *   selectedIndex = selectedIndex,
 *   onOptionClick = { index -> selectedIndex = index },
 *   label = { Text("Feature") },
 * )
 * ```
 */
@Composable
public fun SegmentedSwitch(
    onOptionClick: (selectedIndex: Int) -> Unit,
    optionFirst: @Composable () -> Unit,
    optionSecond: @Composable () -> Unit,
    selectedIndex: Int?,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
) {
    SegmentedSwitch(
        onOptionClick = onOptionClick,
        options = listOf(optionFirst, optionSecond),
        selectedIndex = selectedIndex,
        modifier = modifier,
        label = label,
    )
}

/**
 * A segmented switch displaying multiple options.
 *
 * Renders a segmented switch displaying [options].
 * Modify via custom passed [modifier].
 *
 * Example:
 *
 * ```
 * var selectedIndex by remember { mutableStateOf<Int?>(null) }
 * SegmentedSwitch(
 *   options = listOf(
 *     { Text("First") },
 *     { Text("Second") },
 *     { Text("Third") },
 *   ),
 *   selectedIndex = selectedIndex,
 *   onOptionClick = { index -> selectedIndex = index },
 *   label = { Text("Options") },
 * )
 * ```
 */
@Composable
public fun SegmentedSwitch(
    onOptionClick: (selectedIndex: Int) -> Unit,
    options: List<@Composable () -> Unit>,
    selectedIndex: Int?,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodyNormalMedium,
        ) {
            label()
        }

        Box(
            modifier = Modifier.height(IntrinsicSize.Min),
        ) {
            Surface(
                shape = OrbitTheme.shapes.normal,
                border = BorderStroke(1.dp, OrbitTheme.colors.surface.strong),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                ) {
                    options.forEachIndexed { index, option ->
                        if (index != 0) {
                            VerticalDivider(
                                index = index,
                                selectedIndex = selectedIndex,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    role = Role.Button,
                                    onClick = { onOptionClick(index) },
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 11.dp,
                                )
                                .semantics {
                                    this[SemanticsProperties.Selected] = selectedIndex == index
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            CompositionLocalProvider(
                                LocalTextStyle provides OrbitTheme.typography.bodyNormal
                                    .copy(textAlign = TextAlign.Center),
                                LocalContentEmphasis provides when (selectedIndex) {
                                    null, index -> ContentEmphasis.Normal
                                    else -> ContentEmphasis.Minor
                                },
                            ) {
                                option()
                            }
                        }
                    }
                }
            }

            if (selectedIndex != null) {
                SelectionOutline(
                    selectedIndex = selectedIndex,
                    optionsCount = options.count(),
                )
            }
        }
    }
}

@Composable
private fun VerticalDivider(
    index: Int,
    selectedIndex: Int?,
) {
    val color by animateColorAsState(
        targetValue = when (selectedIndex) {
            index - 1, index -> Color.Unspecified
            else -> OrbitTheme.colors.surface.strong
        },
    )
    Surface(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(),
        color = color,
        content = {},
    )
}

@Composable
private fun SelectionOutline(
    selectedIndex: Int,
    optionsCount: Int,
) {
    val animatedOffset by animateFloatAsState(targetValue = selectedIndex.toFloat())
    val brushColor = OrbitTheme.colors.info.normal
    Canvas(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize(),
        onDraw = {
            val size = size.copy(width = size.width / optionsCount)
            drawRoundRect(
                brush = SolidColor(brushColor),
                topLeft = Offset(
                    x = animatedOffset * size.width,
                    y = 0f,
                ),
                size = size,
                cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                style = Stroke(width = 2.dp.toPx()),
            )
        },
    )
}

@OrbitPreviews
@Composable
internal fun SegmentedSwitchPreview() {
    Preview {
        SegmentedSwitchUnselectedPreview()
        SegmentedSwitchSelectedFirstPreview()
        SegmentedSwitchSelectedSecondPreview()
        SegmentedSwitchThreeOptionsUnselectedPreview()
        SegmentedSwitchThreeOptionsSelectedFirstPreview()
        SegmentedSwitchThreeOptionsSelectedSecondPreview()
        SegmentedSwitchThreeOptionsSelectedThirdPreview()
    }
}

@Composable
private fun SegmentedSwitchUnselectedPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    SegmentedSwitch(
        optionFirst = { Text("Male") },
        optionSecond = { Text("Female") },
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Gender") },
    )
}

@Composable
private fun SegmentedSwitchSelectedFirstPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(0) }
    SegmentedSwitch(
        optionFirst = { Text("Male") },
        optionSecond = { Text("Female") },
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Gender") },
    )
}

@Composable
private fun SegmentedSwitchSelectedSecondPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(1) }
    SegmentedSwitch(
        optionFirst = { Text("San\nFrancisco") },
        optionSecond = { Text("Sacramento") },
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("City") },
    )
}

@Composable
private fun SegmentedSwitchThreeOptionsUnselectedPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    SegmentedSwitch(
        options = listOf(
            { Text("Off") },
            { Text("On") },
            { Text("Remote") },
        ),
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Feature") },
    )
}

@Composable
private fun SegmentedSwitchThreeOptionsSelectedFirstPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(0) }
    SegmentedSwitch(
        options = listOf(
            { Text("Off") },
            { Text("On") },
            { Text("Remote") },
        ),
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Feature") },
    )
}

@Composable
private fun SegmentedSwitchThreeOptionsSelectedSecondPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(1) }
    SegmentedSwitch(
        options = listOf(
            { Text("Off") },
            { Text("On") },
            { Text("Remote") },
        ),
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Feature") },
    )
}

@Composable
private fun SegmentedSwitchThreeOptionsSelectedThirdPreview() {
    var selectedIndex by remember { mutableStateOf<Int?>(2) }
    SegmentedSwitch(
        options = listOf(
            { Text("Off") },
            { Text("On") },
            { Text("Remote") },
        ),
        selectedIndex = selectedIndex,
        onOptionClick = { index -> selectedIndex = index },
        label = { Text("Feature") },
    )
}
