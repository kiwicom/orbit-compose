package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
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
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
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
    error: @Composable (() -> Unit)?,
    info: @Composable (() -> Unit)?,
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

    // Reset input text style color to content color normal.
    val textStyle = LocalTextStyle.current
    val inputTextStyle = textStyle.copy(color = OrbitTheme.colors.content.normal)

    val errorMessage = stringResource(R.string.orbit_field_default_error)

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        modifier = modifier.semantics {
            if (error != null) {
                this.error(errorMessage)
            }
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
    error: @Composable (() -> Unit)?,
    info: @Composable (() -> Unit)?,
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

            val isFocused = interactionSource.collectIsFocusedAsState().value
            val inputState = resolveInputState(isFocused, isError = error != null)

            val transition = updateTransition(inputState, "stateTransition")
            val borderColor by transition.animateBorderColor()
            val glowWidth by transition.animateGlowWidth()

            FieldContent(
                modifier = Modifier
                    .background(OrbitTheme.colors.surface.normal, OrbitTheme.shapes.normal)
                    .borderWithGlow(
                        provideBorderColor = { borderColor },
                        provideGlowColor = { borderColor.copy(borderColor.alpha * GlowOpacity) },
                        provideGlowWidth = { glowWidth },
                    ),
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
                error = error,
                info = if (isFocused) info else null, // Present info only in focused mode.
            )
        }
    }
}

private fun resolveInputState(isFocused: Boolean, isError: Boolean): InputState =
    when (isFocused) {
        true -> when (isError) {
            true -> InputState.FocusedError
            false -> InputState.Focused
        }
        false -> when (isError) {
            true -> InputState.NormalError
            false -> InputState.Normal
        }
    }

@Composable
private fun Transition<InputState>.animateBorderColor(): State<Color> =
    this.animateColor(
        transitionSpec = { tween(AnimationDuration) },
        label = "borderColor",
    ) {
        when (it) {
            InputState.Normal -> Color.Transparent
            InputState.Focused -> OrbitTheme.colors.info.normal
            InputState.NormalError, InputState.FocusedError -> OrbitTheme.colors.critical.normal
        }
    }

@Composable
private fun Transition<InputState>.animateGlowWidth(): State<Dp> =
    this.animateDp(
        transitionSpec = { tween(AnimationDuration) },
        label = "glowWidth",
    ) {
        when (it) {
            InputState.Normal, InputState.NormalError -> 0.dp
            InputState.Focused, InputState.FocusedError -> GlowWidth
        }
    }

private fun Modifier.borderWithGlow(
    provideBorderColor: () -> Color,
    provideGlowColor: () -> Color,
    provideGlowWidth: () -> Dp,
): Modifier = drawWithCache {
    val borderColor = provideBorderColor()
    val glowColor = provideGlowColor()
    val glowWidth = provideGlowWidth()

    val cornerSizePx = CornerSize.toPx()
    val borderWidthPx = BorderWidth.toPx()
    val glowWidthPx = glowWidth.toPx()

    val glowTopLeft = Offset(-glowWidthPx / 2f, -glowWidthPx / 2f)
    val glowSize = Size(size.width + glowWidthPx, size.height + glowWidthPx)
    val glowCornerRadius = CornerRadius(cornerSizePx + glowWidthPx / 2f)
    val glowStyle = Stroke(glowWidthPx)

    val borderTopLeft = Offset(borderWidthPx / 2f, borderWidthPx / 2f)
    val borderSize = Size(size.width - borderWidthPx, size.height - borderWidthPx)
    val borderCornerRadius = CornerRadius(cornerSizePx - borderWidthPx / 2f)
    val borderStyle = Stroke(borderWidthPx)

    onDrawBehind {
        drawRoundRect(glowColor, glowTopLeft, glowSize, glowCornerRadius, glowStyle)
        drawRoundRect(borderColor, borderTopLeft, borderSize, borderCornerRadius, borderStyle)
    }
}

private enum class InputState {
    Normal,
    NormalError,
    Focused,
    FocusedError,
}

private val BorderWidth = 2.dp
private val GlowWidth = 2.dp
private val CornerSize = 6.dp

private const val GlowOpacity = 0.1f
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
