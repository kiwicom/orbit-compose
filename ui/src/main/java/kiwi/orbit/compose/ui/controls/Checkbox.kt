package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme

@Composable
public fun Checkbox(
    checked: Boolean,
    onCheckedChange: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            !checked -> OrbitTheme.colors.surfaceContentTertiary
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = CheckboxAnimationDuration)
    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled && !checked -> OrbitTheme.colors.surfaceContentTertiary.copy(0.19f)
            !enabled -> OrbitTheme.colors.surfaceContentTertiary
            checked -> OrbitTheme.colors.interactive
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = CheckboxAnimationDuration)
    )
    val iconColor = when (enabled) {
        true -> {
            OrbitTheme.colors.interactiveContent
        }
        false -> {
            OrbitTheme.colors.surfaceContentTertiary
                .copy(0.19f)
                .compositeOver(OrbitTheme.colors.surface)
        }
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

    Box {
        Canvas(
            modifier
                .then(selectableModifier)
                .wrapContentSize(Alignment.Center)
                .padding(CheckboxButtonPadding)
                .requiredSize(CheckboxButtonSize)
        ) {
            drawCheckbox(borderColor, backgroundColor)
        }
        if (checked) {
            Icon(
                Icons.Check,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .size(16.dp),
                tint = iconColor,
            )
        }
    }
}

private fun DrawScope.drawCheckbox(borderColor: Color, backgroundColor: Color) {
    val size = CheckboxButtonSize.toPx()
    val cornerRadius = CornerRadius(6.dp.toPx())
    val strokeWidth = 2.dp.toPx()
    val halfStrokeWidth = strokeWidth / 2.0f
    drawRoundRect(
        color = backgroundColor,
        size = Size(size, size),
        cornerRadius = cornerRadius,
        style = Fill,
    )
    drawRoundRect(
        color = borderColor,
        topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
        size = Size(size - strokeWidth, size - strokeWidth),
        cornerRadius = cornerRadius,
        style = Stroke(strokeWidth),
    )
}

private const val CheckboxAnimationDuration = 100

private val CheckboxButtonSize = 20.dp
private val CheckboxButtonPadding = 2.dp
private val CheckboxRippleRadius = 20.dp
