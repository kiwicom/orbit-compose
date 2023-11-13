package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.controls.internal.SwipeableV2State
import kiwi.orbit.compose.ui.controls.internal.swipeAnchors
import kiwi.orbit.compose.ui.controls.internal.swipeableV2
import kotlin.math.roundToInt

@Composable
public fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        icon = null,
        enabled = enabled,
        interactionSource = interactionSource,
    )
}

@Composable
public fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    icon: Painter?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val density = LocalDensity.current
    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null,
            )
        } else {
            Modifier
        }
    val swipeableState = rememberSwipeableStateFor(
        value = checked,
        onValueChange = onCheckedChange ?: {},
        animationSpec = AnimationSpec,
    )
    Box(
        modifier
            .wrapContentSize(Alignment.Center)
            .padding(SwitchPadding)
            .requiredSize(SwitchWidth, SwitchHeight)
            .then(toggleableModifier)
            .swipeableV2(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                enabled = enabled && onCheckedChange != null,
                reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl,
                interactionSource = interactionSource,
            )
            .swipeAnchors(
                state = swipeableState,
                possibleValues = setOf(false, true),
                calculateAnchor = { value, layoutSize ->
                    when (value) {
                        false -> 0f
                        true -> layoutSize.width - with(density) { ThumbDiameter.toPx() }
                    }
                },
            ),
    ) {
        SwitchImpl(
            checked = checked,
            enabled = enabled,
            icon = icon,
            state = swipeableState,
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    icon: Painter?,
    state: SwipeableV2State<Boolean>,
    interactionSource: InteractionSource,
) {
    val interactions = remember { mutableStateListOf<Interaction>() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val hasInteraction = interactions.isNotEmpty()
    val elevation = if (hasInteraction) {
        ThumbPressedElevation
    } else {
        ThumbDefaultElevation
    }
    val mainColor by rememberUpdatedState(
        when {
            checked -> OrbitTheme.colors.info.normal
            else -> OrbitTheme.colors.surface.strong
        }.let {
            when {
                !enabled -> it.copy(alpha = 0.3f)
                else -> it
            }
        },
    )
    SwitchTrack(mainColor)
    SwitchThumb(state, interactionSource, enabled, elevation, icon, mainColor)
}

@Composable
private fun BoxScope.SwitchTrack(mainColor: Color) {
    Canvas(
        Modifier
            .align(Alignment.Center)
            .fillMaxSize(),
    ) {
        drawTrack(mainColor, TrackWidth.toPx(), TrackStrokeWidth.toPx())
    }
}

@Composable
private fun BoxScope.SwitchThumb(
    state: SwipeableV2State<Boolean>,
    interactionSource: InteractionSource,
    enabled: Boolean,
    elevation: Dp,
    icon: Painter?,
    mainColor: Color,
) {
    Spacer(
        Modifier
            .align(Alignment.CenterStart)
            .offset {
                IntOffset(
                    x = state
                        .requireOffset()
                        .roundToInt(),
                    y = 0,
                )
            }
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = false, radius = ThumbRippleRadius),
            )
            .requiredSize(ThumbDiameter)
            .border(ThumbStrokeWidth, Color(0x1907405C), CircleShape) // 10% alpha from 255 = 25 dec = 19 hex
            .padding(ThumbStrokeWidth)
            .then(
                if (enabled) {
                    Modifier.shadow(elevation, CircleShape, clip = false)
                } else {
                    Modifier
                },
            )
            .background(OrbitTheme.colors.surface.main, CircleShape)
            .padding((ThumbDiameter - ThumbInnerDiameter - ThumbStrokeWidth * 2) / 2)
            .then(
                if (icon != null) {
                    Modifier.paint(painter = icon, colorFilter = ColorFilter.tint(mainColor))
                } else {
                    Modifier
                        .padding(ThumbInnerPadding)
                        .background(mainColor, CircleShape)
                },
            ),
    )
}

private fun DrawScope.drawTrack(trackColor: Color, trackWidth: Float, strokeWidth: Float) {
    val strokeRadius = strokeWidth / 2
    drawLine(
        trackColor,
        Offset(strokeRadius + SwitchPadding.toPx(), center.y),
        Offset(trackWidth - strokeRadius + SwitchPadding.toPx(), center.y),
        strokeWidth,
        StrokeCap.Round,
    )
}

@Suppress("SameParameterValue")
@Composable
private fun <T : Any> rememberSwipeableStateFor(
    value: T,
    onValueChange: (T) -> Unit,
    animationSpec: AnimationSpec<Float>,
): SwipeableV2State<T> {
    val swipeableState = remember {
        SwipeableV2State(initialValue = value, animationSpec)
    }
    val forceAnimationCheck = remember { mutableStateOf(false) }
    LaunchedEffect(value, forceAnimationCheck.value) {
        if (value != swipeableState.currentValue) {
            swipeableState.animateTo(value)
        }
    }
    DisposableEffect(swipeableState.currentValue) {
        if (value != swipeableState.currentValue) {
            onValueChange(swipeableState.currentValue)
            forceAnimationCheck.value = !forceAnimationCheck.value
        }
        onDispose { }
    }
    return swipeableState
}

private val SwitchPadding = 2.dp
private val SwitchWidth = 54.dp
private val SwitchHeight = 32.dp

private val TrackWidth = SwitchWidth - SwitchPadding * 2
private val TrackStrokeWidth = SwitchHeight - SwitchPadding * 2
private val ThumbDiameter = SwitchHeight
private val ThumbStrokeWidth = 0.5.dp
private val ThumbInnerDiameter = 16.dp
private val ThumbInnerPadding = 3.dp
private val ThumbRippleRadius = 24.dp

private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

private val ThumbDefaultElevation = 3.dp
private val ThumbPressedElevation = 6.dp

@OrbitPreviews
@Composable
internal fun SwitchPreview() {
    Preview {
        Column {
            Row {
                Switch(
                    checked = false,
                    onCheckedChange = {},
                )
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    icon = Icons.Circle,
                )
                Switch(
                    checked = false,
                    enabled = false,
                    onCheckedChange = {},
                )
                Switch(
                    checked = true,
                    enabled = false,
                    onCheckedChange = {},
                )
            }
            Row {
                Switch(
                    checked = false,
                    onCheckedChange = {},
                    icon = Icons.LockOpen,
                )
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    icon = Icons.Lock,
                )
                Switch(
                    checked = false,
                    enabled = false,
                    onCheckedChange = {},
                    icon = Icons.VisibilityOff,
                )
                Switch(
                    checked = true,
                    enabled = false,
                    onCheckedChange = {},
                    icon = Icons.Visibility,
                )
            }
        }
    }
}
