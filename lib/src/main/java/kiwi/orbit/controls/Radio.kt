package kiwi.orbit.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme

@Composable
public fun Radio(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val borderWidth by animateDpAsState(
        targetValue = if (selected) 6.dp else 2.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration)
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled || !selected -> OrbitTheme.colors.surfaceContentTertiary
            else -> OrbitTheme.colors.interactive
        },
        animationSpec = tween(durationMillis = RadioAnimationDuration)
    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled && !selected -> OrbitTheme.colors.surfaceContentTertiary.copy(0.19f)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = RadioAnimationDuration)
    )
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = RadioRippleRadius
                )
            )
        } else {
            Modifier
        }

    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioPadding)
            .requiredSize(RadioSize)
    ) {
        drawRadio(borderWidth, borderColor, backgroundColor)
    }
}

private fun DrawScope.drawRadio(borderWidth: Dp, borderColor: Color, backgroundColor: Color) {
    val borderWidthPx = borderWidth.toPx()
    drawCircle(backgroundColor, RadioRadiusSize.toPx(), style = Fill)
    drawCircle(borderColor, RadioRadiusSize.toPx() - borderWidthPx / 2, style = Stroke(borderWidthPx))
}

private const val RadioAnimationDuration = 100

private val RadioSize = 20.dp
private val RadioRadiusSize = RadioSize / 2
private val RadioPadding = 2.dp
private val RadioRippleRadius = 20.dp
