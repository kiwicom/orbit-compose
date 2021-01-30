package kiwi.orbit.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Interaction
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kiwi.orbit.OrbitTheme
import kiwi.orbit.R
import kiwi.orbit.icons.AlertCircle
import kiwi.orbit.icons.Icons
import kotlinx.coroutines.delay

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    label: (@Composable () -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onImeActionPerformed: (ImeAction) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionState: InteractionState = remember { InteractionState() },
) {
    Column(modifier) {
        var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
        val textFieldValue = textFieldValueState.copy(text = value)

        if (label != null) {
            Box(Modifier.padding(bottom = 4.dp)) {
                label()
            }
        }

        @Suppress("MoveVariableDeclarationIntoWhen")
        val isFocused = interactionState.contains(Interaction.Focused)
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

        val transition = updateTransition(inputState)
        val borderColor = transition.animateColor(
            transitionSpec = { tween(durationMillis = AnimationDuration) }
        ) {
            when (it) {
                InputState.Normal -> Color.Transparent
                InputState.Focused -> OrbitTheme.colors.secondary
                InputState.NormalError -> OrbitTheme.colors.critical
                InputState.FocusedError -> OrbitTheme.colors.critical
            }
        }
        val backgroundColor = transition.animateColor(
            transitionSpec = { tween(durationMillis = AnimationDuration) }
        ) {
            when (it) {
                InputState.Focused, InputState.FocusedError -> OrbitTheme.colors.surface
                else -> OrbitTheme.colors.surfaceSecondary
            }
        }
        val errorAlpha = transition.animateFloat(
            transitionSpec = { tween(durationMillis = AnimationDuration) }
        ) {
            when (it) {
                InputState.NormalError, InputState.FocusedError -> 1f
                else -> 0f
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
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            maxLines = maxLines,
            onImeActionPerformed = onImeActionPerformed,
            visualTransformation = visualTransformation,
            interactionState = interactionState,
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .border(1.dp, borderColor.value, MaterialTheme.shapes.small)
                        .background(backgroundColor.value, MaterialTheme.shapes.small)
                        .padding(12.dp)
                ) {
                    if (placeholder != null && textFieldValue.text.isEmpty()) {
                        Box(Modifier.zIndex(1f)) {
                            ProvideTextStyle(textStyle, placeholder)
                        }
                    }
                    innerTextField()
                }
            }
        )

        var errorState by remember { mutableStateOf(error) }
        if (error == null) {
            LaunchedEffect(this) {
                delay(AnimationDuration.toLong())
                errorState = null
            }
        } else {
            errorState = error
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(errorAlpha.value)
                .animateContentSize()
        ) {
            if (errorState != null) {
                Icon(
                    Icons.AlertCircle,
                    contentDescription = stringResource(id = R.string.orbit_cd_text_field_error_icon),
                    modifier = Modifier
                        .padding(2.dp)
                        .size(18.dp),
                    tint = OrbitTheme.colors.critical,
                )
                ProvideTextStyle(value = TextStyle.Default.copy(color = OrbitTheme.colors.critical)) {
                    errorState?.invoke()
                }
            }
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
