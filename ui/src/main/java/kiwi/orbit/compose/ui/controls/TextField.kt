package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.field.FieldContent
import kiwi.orbit.compose.ui.controls.field.FieldLabel
import kiwi.orbit.compose.ui.controls.field.FieldMessage
import kiwi.orbit.compose.ui.controls.internal.ColumnWithMinConstraints
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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
    maxLines: Int = Int.MAX_VALUE,
    bringIntoView: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val autoBringIntoViewSetupModifier: Modifier
    val autoBringIntoViewFocusModifier: Modifier

    if (bringIntoView) {
        val coroutineScope = rememberCoroutineScope()
        val bringIntoViewRequester = remember { BringIntoViewRequester() }
        val layoutCoordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }
        val scaffoldBottomPadding = with(LocalDensity.current) {
            LocalScaffoldPadding.current.calculateBottomPadding().toPx()
        }

        autoBringIntoViewSetupModifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .onGloballyPositioned { layoutCoordinates.value = it }
        autoBringIntoViewFocusModifier = Modifier.onFocusEvent {
            if (it.isFocused || it.hasFocus) {
                coroutineScope.launch {
                    delay(150) // Bulgarian constant, wait a bit until keyboard gets opened.
                    val size = layoutCoordinates.value?.size?.toSize() ?: return@launch
                    val paddedSize = size.copy(height = size.height + scaffoldBottomPadding)
                    bringIntoViewRequester.bringIntoView(paddedSize.toRect())
                }
            }
        }
    } else {
        autoBringIntoViewSetupModifier = Modifier
        autoBringIntoViewFocusModifier = Modifier
    }

    val errorMessage = stringResource(R.string.orbit_field_default_error)
    ColumnWithMinConstraints(
        modifier
            .semantics {
                if (error != null) {
                    this.error(errorMessage)
                }
            }
            .then(autoBringIntoViewSetupModifier)
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
            var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
            val textFieldValue = textFieldValueState.copy(text = value)

            if (label != null) {
                FieldLabel(label)
            }

            @Suppress("MoveVariableDeclarationIntoWhen")
            val isFocused = interactionSource.collectIsFocusedAsState().value
            val inputState: InputState = when (isFocused) {
                true -> when (error != null) {
                    true -> InputState.FocusedError
                    false -> InputState.Focused
                }
                false -> when (error != null) {
                    true -> InputState.NormalError
                    false -> InputState.Normal
                }
            }

            // If color is not provided via the text style, use content color as a default
            val textStyle = LocalTextStyle.current
            val mergedTextStyle = textStyle.copy(color = OrbitTheme.colors.content.normal)

            val transition = updateTransition(inputState, "stateTransition")
            val borderColor = transition.animateColor(
                transitionSpec = { tween(durationMillis = AnimationDuration) },
                label = "borderColor"
            ) {
                when (it) {
                    InputState.Normal -> Color.Transparent
                    InputState.Focused, InputState.FocusedError -> OrbitTheme.colors.info.normal
                    InputState.NormalError -> OrbitTheme.colors.critical.normal
                }
            }

            BasicTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValueState = it
                    if (value != it.text) {
                        onValueChange(it.text)
                    }
                },
                modifier = Modifier
                    .then(autoBringIntoViewFocusModifier)
                    .border(1.dp, borderColor.value, OrbitTheme.shapes.normal)
                    .background(OrbitTheme.colors.surface.subtle, OrbitTheme.shapes.normal),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = mergedTextStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(OrbitTheme.colors.info.normal),
                decorationBox = { innerTextField ->
                    FieldContent(
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
                }
            )

            FieldMessage(
                error = error,
                info = if (isFocused) info else null, // Present info only in focused mode.
            )
        }
    }
}

private enum class InputState {
    Normal,
    NormalError,
    Focused,
    FocusedError,
}

private const val AnimationDuration = 150
