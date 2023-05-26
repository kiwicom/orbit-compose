package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.editableText
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.field.FieldLabel
import kiwi.orbit.compose.ui.controls.field.FieldMessage
import kiwi.orbit.compose.ui.controls.internal.ColumnWithMinConstraints
import kiwi.orbit.compose.ui.controls.internal.OrbitElevations
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun <T> SelectField(
    value: String,
    options: List<T>,
    onOptionSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    label: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    optionContent: @Composable RowScope.(option: T) -> Unit,
) {
    ColumnWithMinConstraints(
        modifier
            .semantics {
                editableText = AnnotatedString(value)
            },
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        if (label != null) {
            FieldLabel(label)
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            ClickableFieldBox(
                value = value,
                isError = error != null,
                onClick = {},
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = {
                    val iconRotation by animateFloatAsState(
                        targetValue = if (expanded) 0f else 180f,
                        animationSpec = tween(),
                        label = "SelectDropdownChevronRotation",
                    )
                    Icon(
                        painter = Icons.ChevronUp,
                        contentDescription = null, // TODO:
                        modifier = Modifier.rotate(iconRotation),
                        emphasis = ContentEmphasis.Minor,
                    )
                },
                singleLine = true,
                maxLines = maxLines,
                minLines = minLines,
                interactionSource = interactionSource,
                // Workaround util https://issuetracker.google.com/issues/207695810 gets fixed.
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )
            OrbitElevations(neutralize = 3.dp) { // MenuTokens.ContainerElevation
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = {
                                Row {
                                    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                                        optionContent(selectionOption)
                                    }
                                }
                            },
                            onClick = {
                                onOptionSelect(selectionOption)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }

        FieldMessage(error, info)
    }
}

@OrbitPreviews
@Composable
internal fun SelectFieldPreview() {
    Preview {
        SelectField(
            value = "A",
            options = listOf("A", "B"),
            onOptionSelect = {},
            label = { Text("Nationality") },
            info = { Text("Attach JPEG.") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Airplane, contentDescription = null) },
        ) {
        }
    }
}
