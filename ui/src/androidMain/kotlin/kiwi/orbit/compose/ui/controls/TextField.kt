package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.field.FieldContent
import kiwi.orbit.compose.ui.controls.field.FieldLabel
import kiwi.orbit.compose.ui.controls.field.FieldMessage
import kiwi.orbit.compose.ui.controls.field.LabelLastBaseLine
import kiwi.orbit.compose.ui.controls.internal.ColumnWithMinConstraints
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

/**
 * TextFiled control allowing a text single-line or multi-line input.
 *
 * To align multiple text fields vertically next to each other, utilize
 * [LabelLastBaseLine] alignment line.
 */
@Composable
public fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    error: @Composable () -> Unit = {},
    info: @Composable () -> Unit = {},
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        error = error,
        info = info,
        additionalContent = null,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        onLeadingIconClick = onLeadingIconClick,
        trailingIcon = trailingIcon,
        onTrailingIconClick = onTrailingIconClick,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        modifier = modifier,
    )
}

@Suppress("LongParameterList")
@Composable
internal fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    readOnly: Boolean,
    label: @Composable (() -> Unit)?,
    error: @Composable () -> Unit,
    info: @Composable () -> Unit,
    additionalContent: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    onLeadingIconClick: (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    onTrailingIconClick: (() -> Unit)?,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean,
    maxLines: Int,
    minLines: Int,
    visualTransformation: VisualTransformation,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    // Reset input text style color to content color norma.
    val textStyle = LocalTextStyle.current
    val inputTextStyle = textStyle.copy(color = OrbitTheme.colors.content.normal)

    @Suppress("UNUSED_VARIABLE") val errorMessage = stringResource(R.string.orbit_field_default_error)

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        modifier = modifier.semantics {
//            if (isError) {
//                this.error(errorMessage)
//            }
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = inputTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(OrbitTheme.colors.info.normal),
        decorationBox = { innerTextField ->
            TextFiledDecorationBox(
                innerTextField = innerTextField,
                textFieldValue = textFieldValue,
                label = label,
                error = error,
                info = info,
                additionalContent = additionalContent,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                onLeadingIconClick = onLeadingIconClick,
                trailingIcon = trailingIcon,
                onTrailingIconClick = onTrailingIconClick,
                singleLine = singleLine,
                interactionSource = interactionSource,
            )
        },
    )
}

@Suppress("LongParameterList")
@Composable
private fun TextFiledDecorationBox(
    innerTextField: @Composable () -> Unit,
    textFieldValue: TextFieldValue,
    label: @Composable (() -> Unit)?,
    error: @Composable () -> Unit,
    info: @Composable () -> Unit,
    additionalContent: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    onLeadingIconClick: (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    onTrailingIconClick: (() -> Unit)?,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource,
) {
    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
        ColumnWithMinConstraints {
            if (label != null) {
                FieldLabel(label)
            }

            val isFocused = interactionSource.collectIsFocusedAsState()
            val isError = remember { mutableStateOf(false) }

            val borderColor = animateColorAsState(
                targetValue = when {
                    isError.value -> OrbitTheme.colors.critical.normal
                    isFocused.value -> OrbitTheme.colors.info.normal
                    else -> Color.Transparent
                },
                animationSpec = tween(durationMillis = AnimationDuration),
                label = "borderColor",
            )

            FieldContent(
                modifier = Modifier
                    .border(1.dp, borderColor.value, OrbitTheme.shapes.normal)
                    .background(OrbitTheme.colors.surface.normal, OrbitTheme.shapes.normal),
                fieldContent = innerTextField,
                placeholder = when (textFieldValue.text.isEmpty()) {
                    true -> placeholder
                    false -> null
                },
                leadingIcon = leadingIcon,
                onLeadingIconClick = onLeadingIconClick,
                trailingIcon = trailingIcon,
                onTrailingIconClick = onTrailingIconClick,
                singleLine = singleLine,
            )

            additionalContent?.invoke()

            FieldMessage(
                showInfo = isFocused.value,
                onErrorResolved = { isError.value = it },
                error = error,
                info = info,
            )
        }
    }
}

private const val AnimationDuration = 150

@OrbitPreviews
@Composable
internal fun TextFieldPreview() {
    Preview {
        TextField(
            value = "A",
            onValueChange = {},
            label = { Text("Surname") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Diamond, contentDescription = null) },
            trailingIcon = { Icon(Icons.Close, contentDescription = null) },
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Surname") },
            placeholder = { Text("Enter your surname.") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Ai, contentDescription = null) },
            trailingIcon = { Icon(Icons.Close, contentDescription = null) },
            error = { Text("Error message") },
        )
    }
}
