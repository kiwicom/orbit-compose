package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(modifier) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
            var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
            val textFieldValue = textFieldValueState.copy(text = value)

            if (label != null) {
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal.copy(fontWeight = FontWeight.Medium)) {
                    Box(Modifier.padding(bottom = 4.dp)) {
                        label()
                    }
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
            val mergedTextStyle = textStyle.copy(color = OrbitTheme.colors.content.normal)

            val transition = updateTransition(inputState, "stateTransition")
            val borderColor = transition.animateColor(
                transitionSpec = { tween(durationMillis = AnimationDuration) },
                label = "borderColor"
            ) {
                when (it) {
                    InputState.Normal -> Color.Transparent
                    InputState.Focused -> OrbitTheme.colors.interactive.main
                    InputState.NormalError, InputState.FocusedError -> OrbitTheme.colors.critical.main
                }
            }
            val backgroundColor = transition.animateColor(
                transitionSpec = { tween(durationMillis = AnimationDuration) },
                label = "backgroundColor"
            ) {
                when (it) {
                    InputState.Focused, InputState.FocusedError -> OrbitTheme.colors.surface.main
                    else -> OrbitTheme.colors.surface.subtle
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
                cursorBrush = SolidColor(OrbitTheme.colors.interactive.main),
                decorationBox = { innerTextField ->
                    Layout(
                        content = {
                            if (leadingIcon != null) {
                                Box(Modifier.layoutId("leading")) {
                                    if (onLeadingIconClick != null) {
                                        IconButton(
                                            onClick = onLeadingIconClick,
                                            rippleRadius = RippleRadius,
                                        ) {
                                            leadingIcon()
                                        }
                                    } else {
                                        leadingIcon()
                                    }
                                }
                            }
                            if (trailingIcon != null) {
                                Box(Modifier.layoutId("trailing")) {
                                    if (onTrailingIconClick != null) {
                                        IconButton(
                                            onClick = onTrailingIconClick,
                                            rippleRadius = RippleRadius,
                                        ) {
                                            trailingIcon()
                                        }
                                    } else {
                                        trailingIcon()
                                    }
                                }
                            }
                            if (placeholder != null && textFieldValue.text.isEmpty()) {
                                Box(Modifier.layoutId("placeholder")) {
                                    ProvideContentEmphasis(ContentEmphasis.Minor, content = placeholder)
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

                        val iconPaddingWidth = (24.dp - RippleRadius) * 2
                        val trailingPadding =
                            if (onTrailingIconClick != null) padding - iconPaddingWidth.roundToPx() else padding
                        val trailingPlaceable = measurables.find { it.layoutId == "trailing" }
                            ?.measure(constraints.offset(horizontal = -leadingWidth))
                        val trailingWidth = trailingPlaceable?.width?.plus(trailingPadding) ?: 0

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

            Message(error, info, textStyle)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Message(
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    textStyle: TextStyle
) {
    val state = when {
        error != null -> Message.Error(error)
        info != null -> Message.Info(info)
        else -> null
    }
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            if (targetState == null || initialState == null) {
                val enter = slideInVertically(animationSpec = tween(AnimationDuration)) +
                    fadeIn(animationSpec = tween(AnimationDuration))
                val exit = slideOutVertically(animationSpec = tween(AnimationDuration)) +
                    fadeOut(animationSpec = tween(AnimationDuration))
                val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                enter with exit using size
            } else {
                val enter = fadeIn(animationSpec = tween(AnimationDuration))
                val exit = fadeOut(animationSpec = tween(AnimationDuration))
                val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                enter with exit using size
            }
        },
    ) { message ->
        if (message != null) {
            Row(Modifier.padding(top = 6.dp)) {
                val icon = when (message) {
                    is Message.Error -> Icons.AlertCircle
                    is Message.Info -> Icons.InformationCircle
                }
                val tintColor = when (message) {
                    is Message.Error -> OrbitTheme.colors.critical.main
                    is Message.Info -> OrbitTheme.colors.interactive.main
                }
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 2.dp, end = 4.dp)
                        .size(16.dp),
                    tint = tintColor,
                )
                ProvideMergedTextStyle(textStyle.copy(color = tintColor)) {
                    message.content.invoke()
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
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var showRawInput by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        error = error,
        info = info,
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
            )
        },
        onTrailingIconClick = { showRawInput = !showRawInput },
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

private sealed class Message(
    open val content: @Composable (() -> Unit)
) {
    data class Error(override val content: @Composable () -> Unit) : Message(content)
    data class Info(override val content: @Composable () -> Unit) : Message(content)
}

private const val AnimationDuration = 150
private val RippleRadius = 20.dp
