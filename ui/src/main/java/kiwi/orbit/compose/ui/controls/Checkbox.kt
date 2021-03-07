package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
public fun Checkbox(
    checked: Boolean,
    onCheckedChange: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            !checked -> OrbitTheme.colors.surface.disabled
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = CheckboxAnimationDuration)
    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled && !checked -> OrbitTheme.colors.surface.subtle
            !enabled -> OrbitTheme.colors.surface.disabled
            checked -> OrbitTheme.colors.interactive.main
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = CheckboxAnimationDuration)
    )
    val iconColor = when (enabled) {
        true -> OrbitTheme.colors.interactive.onMain
        false -> OrbitTheme.colors.surface.subtle
    }
    val selectableModifier =
        if (onCheckedChange != null) {
            Modifier.triStateToggleable(
                state = if (checked) ToggleableState.On else ToggleableState.Off,
                onClick = onCheckedChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = CheckboxRippleRadius
                )
            )
        } else {
            Modifier
        }

    val errorAlpha by animateFloatAsState(
        targetValue = if (isError && enabled) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = CheckboxAnimationDuration)
    )
    val errorStrokeColor = OrbitTheme.colors.critical.main
    val errorShadowColor = OrbitTheme.colors.critical.subtle

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier
                .then(selectableModifier)
                .requiredSize(CheckboxSize)
        ) {
            drawCheckbox(borderColor, backgroundColor, errorAlpha)
            drawError(errorStrokeColor, errorShadowColor, errorAlpha)
        }
        val drawable = animatedVectorResource(R.drawable.avd_check)
        Icon(
            painter = drawable.painterFor(atEnd = checked),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = iconColor,
        )
    }
}

private fun DrawScope.drawCheckbox(borderColor: Color, backgroundColor: Color, errorAlpha: Float) {
    val hasError = errorAlpha != 0.0f
    val errorShift = if (hasError) 0.5.dp.toPx() else 0f

    val checkboxSize = CheckboxSize.toPx()
    val checkboxBorderWidth = CheckboxBorderWidth.toPx()
    val checkboxBorderHalfWidth = checkboxBorderWidth / 2.0f
    val checkboxCornerRadius = CornerRadius(CheckboxCornerRadius.toPx())
    drawRoundRect(
        color = backgroundColor,
        topLeft = Offset(0f + errorShift, 0f + errorShift),
        size = Size(checkboxSize - 2 * errorShift, checkboxSize - 2 * errorShift),
        cornerRadius = checkboxCornerRadius,
        style = Fill,
    )
    drawRoundRect(
        color = borderColor,
        topLeft = Offset(checkboxBorderHalfWidth, checkboxBorderHalfWidth),
        size = Size(checkboxSize - checkboxBorderWidth, checkboxSize - checkboxBorderWidth),
        cornerRadius = checkboxCornerRadius,
        style = Stroke(checkboxBorderWidth),
    )
}

private fun DrawScope.drawError(borderColor: Color, shadowColor: Color, alpha: Float) {
    if (alpha == 0.0f) return

    val shadowRectShift = (ErrorShadowWidth / 2.0f).toPx()
    val shadowRectSize = (ErrorShadowSize - ErrorShadowWidth).toPx()
    drawRoundRect(
        color = shadowColor,
        topLeft = Offset(-shadowRectShift, -shadowRectShift),
        size = Size(shadowRectSize, shadowRectSize),
        cornerRadius = CornerRadius(ErrorShadowCornerRadius.toPx()),
        style = Stroke(ErrorShadowWidth.toPx()),
    )

    val errorRectShift = (CheckboxBorderWidth / 2.0f).toPx()
    val errorRectSize = (CheckboxSize - CheckboxBorderWidth).toPx()
    drawRoundRect(
        color = borderColor,
        topLeft = Offset(errorRectShift, errorRectShift),
        size = Size(errorRectSize, errorRectSize),
        cornerRadius = CornerRadius(CheckboxCornerRadius.toPx()),
        style = Stroke(CheckboxBorderWidth.toPx()),
    )
}

private const val CheckboxAnimationDuration = 100

private val CheckboxSize = 20.dp
private val CheckboxBorderWidth = 2.dp
private val CheckboxCornerRadius = 6.dp
private val CheckboxRippleRadius = 20.dp
private val ErrorShadowWidth = 2.dp
private val ErrorShadowSize = CheckboxSize + ErrorShadowWidth * 2
private val ErrorShadowCornerRadius = CheckboxCornerRadius + ErrorShadowWidth
