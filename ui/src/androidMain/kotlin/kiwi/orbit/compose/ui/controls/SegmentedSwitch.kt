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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.field.FieldMessage
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
 * An information or error footer can be added to the field.
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
 *   info = { Text("This is contextual information.") },
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
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
) {
    SegmentedSwitch(
        onOptionClick = onOptionClick,
        options = listOf(optionFirst, optionSecond),
        selectedIndex = selectedIndex,
        modifier = modifier,
        label = label,
        error = error,
        info = info,
    )
}

/**
 * A segmented switch displaying multiple options.
 *
 * Renders a segmented switch displaying [options].
 * Modify via custom passed [modifier].
 * An information or error footer can be added to the field.
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
 *   error = if (selectedIndex == null) {
 *     { Text("Please select an option.") }
 *   } else {
 *     null
 *   },
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
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
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
            val borderColor by animateColorAsState(
                targetValue = when (error == null) {
                    true -> OrbitTheme.colors.surface.normal
                    false -> OrbitTheme.colors.critical.normal
                },
                label = "SegmentedSwitchBorderColor",
            )

            Surface(
                shape = OrbitTheme.shapes.normal,
                border = BorderStroke(2.dp, borderColor),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                ) {
                    options.forEachIndexed { index, option ->
                        if (index != 0) {
                            VerticalDivider(
                                hasError = error != null,
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
                                    selected = selectedIndex == index
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            CompositionLocalProvider(
                                LocalTextStyle provides OrbitTheme.typography.bodyNormal
                                    .copy(textAlign = TextAlign.Center),
                                LocalContentEmphasis provides ContentEmphasis.Normal,
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

        FieldMessage(
            error = error,
            info = info,
        )
    }
}

@Composable
private fun VerticalDivider(
    hasError: Boolean,
    index: Int,
    selectedIndex: Int?,
) {
    val bordersSelected = selectedIndex == index - 1 || selectedIndex == index
    val color by animateColorAsState(
        targetValue = when {
            hasError -> OrbitTheme.colors.critical.normal
            bordersSelected -> OrbitTheme.colors.surface.main.copy(alpha = 0f)
            else -> OrbitTheme.colors.surface.normal
        },
        label = "SegmentedSwitchDividerColor",
    )
    Surface(
        modifier = Modifier
            .width(2.dp)
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
    val animatedOffset by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        label = "SegmentedSwitchSelectedOffset",
    )
    val brushColor = OrbitTheme.colors.info.normal
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Canvas(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize(),
        onDraw = {
            val rectSize = size.copy(width = size.width / optionsCount)
            val topLeft = Offset(
                x = when (isRtl) {
                    false -> animatedOffset * rectSize.width
                    true -> size.width - ((animatedOffset + 1) * rectSize.width)
                },
                y = 0f,
            )
            drawRoundRect(
                brush = SolidColor(brushColor),
                topLeft = topLeft,
                size = rectSize,
                cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                style = Stroke(width = 2.dp.toPx()),
            )
        },
    )
}

@OrbitPreviews
@Composable
internal fun SegmentedSwitchPreview() {
    Preview(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
        SegmentedSwitchUnselectedPreview()
        SegmentedSwitchSelectedPreview()
        SegmentedSwitchThreeOptionsUnselectedPreview()
        SegmentedSwitchThreeOptionsSelectedPreview()
        SegmentedSwitchWithInfoPreview()
        SegmentedSwitchWithErrorPreview()
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
private fun SegmentedSwitchSelectedPreview() {
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
private fun SegmentedSwitchThreeOptionsSelectedPreview() {
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
private fun SegmentedSwitchWithInfoPreview() {
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
        info = { Text("This is valuable information.") },
    )
}

@Composable
private fun SegmentedSwitchWithErrorPreview() {
    SegmentedSwitch(
        options = listOf(
            { Text("Off") },
            { Text("On") },
            { Text("Remote") },
        ),
        selectedIndex = null,
        onOptionClick = {},
        label = { Text("Feature") },
        error = { Text("You haven't completed this step.") },
    )
}
