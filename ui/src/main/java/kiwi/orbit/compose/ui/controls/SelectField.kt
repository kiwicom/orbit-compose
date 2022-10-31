package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.editableText
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.field.FieldLabel
import kiwi.orbit.compose.ui.controls.field.FieldMessage
import kiwi.orbit.compose.ui.controls.internal.ColumnWithMinConstraints
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun <T> SelectField(
    value: String,
    options: List<T>,
    onOptionSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    optionContent: @Composable RowScope.(option: T) -> Unit,
) {
    ColumnWithMinConstraints(
        modifier
            .semantics {
                editableText = AnnotatedString(value)
            },
    ) {
        var expanded by remember { mutableStateOf(false) }

        if (label != null) {
            FieldLabel(label)
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded // todo: switch to $it when fixed in Material
            },
        ) {
            ClickableFieldBox(
                value = value,
                isError = error != null,
                onClick = { },
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = {
                    val iconRotation by animateFloatAsState(
                        targetValue = if (expanded) 0f else 180f,
                        animationSpec = tween(),
                    )
                    Icon(
                        painter = Icons.ChevronUp,
                        contentDescription = null, // TODO:
                        modifier = Modifier.rotate(iconRotation),
                        emphasis = ContentEmphasis.Minor,
                    )
                },
                singleLine = true,
                // Workaround util https://issuetracker.google.com/issues/207695810 gets fixed.
                modifier = Modifier.fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelect(selectionOption)
                            expanded = false
                        },
                    ) {
                        ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                            optionContent(selectionOption)
                        }
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
