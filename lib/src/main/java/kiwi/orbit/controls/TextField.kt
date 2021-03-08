package kiwi.orbit.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.OrbitTheme
import kiwi.orbit.R
import kiwi.orbit.icons.Icons
import kotlinx.coroutines.delay

@Composable
public fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(modifier) {
        ProvideTextStyle(OrbitTheme.typography.bodyNormal) {
            var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
            val textFieldValue = textFieldValueState.copy(text = value)

            if (label != null) {
                Box(Modifier.padding(bottom = 4.dp)) {
                    label()
                }
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
            val mergedTextStyle = textStyle.copy(color = OrbitTheme.colors.surfaceContent)

            val transition = updateTransition(inputState)
            val borderColor = transition.animateColor(
                transitionSpec = { tween(durationMillis = AnimationDuration) }
            ) {
                when (it) {
                    InputState.Normal -> Color.Transparent
                    InputState.Focused -> OrbitTheme.colors.interactive
                    InputState.NormalError -> OrbitTheme.colors.critical
                    InputState.FocusedError -> OrbitTheme.colors.critical
                }
            }
            val backgroundColor = transition.animateColor(
                transitionSpec = { tween(durationMillis = AnimationDuration) }
            ) {
                when (it) {
                    InputState.Focused, InputState.FocusedError -> OrbitTheme.colors.surface
                    else -> OrbitTheme.colors.surfaceAlt
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
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor.value, OrbitTheme.shapes.normal)
                    .background(backgroundColor.value, OrbitTheme.shapes.normal),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = mergedTextStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(OrbitTheme.colors.interactive),
                decorationBox = { innerTextField ->
                    Layout(
                        content = {
                            if (leadingIcon != null) {
                                Box(Modifier.layoutId("leading")) {
                                    leadingIcon()
                                }
                            }
                            if (trailingIcon != null) {
                                Box(Modifier.layoutId("trailing")) {
                                    trailingIcon()
                                }
                            }
                            if (placeholder != null && textFieldValue.text.isEmpty()) {
                                Box(Modifier.layoutId("placeholder")) {
                                    ProvideTextStyle(
                                        textStyle.copy(color = OrbitTheme.colors.surfaceContentAlt),
                                        placeholder
                                    )
                                }
                            }
                            Box(Modifier.layoutId("textField")) {
                                innerTextField()
                            }
                        },
                    ) { measurables, incomingConstraints ->
                        val constraints = incomingConstraints.copy(minWidth = 0, minHeight = 0)
                        val padding = 12.dp.roundToPx()

                        val leadingPlaceable = measurables.find { it.layoutId == "leading" }
                            ?.measure(constraints)
                        val leadingWidth = leadingPlaceable?.width?.plus(padding) ?: 0

                        val trailingPlaceable = measurables.find { it.layoutId == "trailing" }
                            ?.measure(constraints.offset(horizontal = -leadingWidth))
                        val trailingWidth = trailingPlaceable?.width?.plus(padding) ?: 0

                        val occupiedHorizontally = padding * 2 + leadingWidth + trailingWidth
                        val textFieldConstraints = incomingConstraints
                            .copy(minHeight = 0)
                            .offset(horizontal = -occupiedHorizontally)

                        val placeholderPlaceable =
                            measurables.find { it.layoutId == "placeholder" }?.measure(textFieldConstraints)
                        val textFieldPlaceable =
                            measurables.first { it.layoutId == "textField" }.measure(textFieldConstraints)

                        val width = constraints.maxWidth
                        val height = textFieldPlaceable.height + padding * 2

                        val textVerticalPosition = if (singleLine) {
                            Alignment.CenterVertically.align(textFieldPlaceable.height, height)
                        } else {
                            padding
                        }

                        layout(width, height) {
                            leadingPlaceable?.placeRelative(
                                padding,
                                Alignment.CenterVertically.align(leadingPlaceable.height, height)
                            )
                            trailingPlaceable?.placeRelative(
                                width - trailingWidth,
                                Alignment.CenterVertically.align(trailingPlaceable.height, height)
                            )
                            placeholderPlaceable?.placeRelative(
                                padding + leadingWidth,
                                textVerticalPosition,
                                zIndex = 1f
                            )
                            textFieldPlaceable.placeRelative(padding + leadingWidth, textVerticalPosition)
                        }
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
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .alpha(errorAlpha.value)
                    .animateContentSize()
            ) {
                if (errorState != null) {
                    Icon(
                        Icons.AlertCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 2.dp, end = 4.dp)
                            .size(16.dp),
                        tint = OrbitTheme.colors.critical,
                    )
                    ProvideTextStyle(textStyle.copy(color = OrbitTheme.colors.critical)) {
                        errorState?.invoke()
                    }
                }
            }
        }
    }
}

@Composable
public fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: (@Composable () -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var showRawInput by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        error = error,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = {
            Icon(
                when (showRawInput) {
                    true -> Icons.VisibilityOff
                    false -> Icons.Visibility
                },
                contentDescription = when (showRawInput) {
                    true -> stringResource(id = R.string.orbit_cd_text_field_hide_password)
                    false -> stringResource(id = R.string.orbit_cd_text_field_show_password)
                },
                Modifier.clickable(
                    onClick = { showRawInput = !showRawInput },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = RippleRadius)
                )
            )
        },
        keyboardOptions = keyboardOptions.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = when (showRawInput) {
            true -> VisualTransformation.None
            false -> PasswordVisualTransformation()
        },
        interactionSource = interactionSource,
    )
}

private enum class InputState {
    Normal,
    NormalError,
    Focused,
    FocusedError,
}

private const val AnimationDuration = 150
private val RippleRadius = 20.dp
