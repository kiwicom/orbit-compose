package kiwi.orbit.compose.ui.controls

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.SliderPositions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor
import kotlin.math.abs

/**
 * Slider component for a single float value.
 *
 * The [valueLabel] slot may stay empty, render the value outside Slider component.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueLabel: @Composable (Float) -> Unit,
    startLabel: @Composable () -> Unit,
    endLabel: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @IntRange(from = 0)
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
            .sliderSemantics(value, enabled, onValueChange, onValueChangeFinished, valueRange, steps),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CompositionLocalProvider(
                LocalContentColor provides OrbitTheme.colors.info.normal,
                LocalTextStyle provides LocalTextStyle.current.merge(OrbitTheme.typography.bodyNormal),
            ) {
                valueLabel(value)
            }
            CompositionLocalProvider(
                LocalMinimumInteractiveComponentEnforcement provides false,
            ) {
                androidx.compose.material3.Slider(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    valueRange = valueRange,
                    steps = steps,
                    onValueChangeFinished = onValueChangeFinished,
                    interactionSource = interactionSource,
                    thumb = {
                        Thumb(
                            interactionSource = interactionSource,
                            enabled = enabled,
                        )
                    },
                    track = { sliderPositions ->
                        Track(enabled, sliderPositions)
                    },
                )
            }
        }
        Row {
            Box(Modifier.weight(1f)) {
                ProvideContentEmphasis(ContentEmphasis.Minor) {
                    ProvideMergedTextStyle(
                        OrbitTheme.typography.bodyNormal,
                        content = startLabel,
                    )
                }
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.TopEnd) {
                ProvideContentEmphasis(ContentEmphasis.Minor) {
                    ProvideMergedTextStyle(
                        OrbitTheme.typography.bodyNormal.copy(textAlign = TextAlign.End),
                        content = endLabel,
                    )
                }
            }
        }
    }
}

@Composable
private fun Thumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thumbSize: DpSize = DpSize(24.dp, 24.dp),
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

    val elevation = if (interactions.isNotEmpty()) {
        OrbitTheme.elevations.Level1
    } else {
        OrbitTheme.elevations.Level2
    }
    val thumbBgColor = rememberUpdatedState(
        if (enabled) {
            OrbitTheme.colors.info.normal
        } else {
            OrbitTheme.colors.surface.disabled
        },
    )

    Spacer(
        modifier
            .size(thumbSize)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 40.dp / 2,
                ),
            )
            .hoverable(interactionSource = interactionSource)
            .surface(
                shape = CircleShape,
                backgroundColor = surfaceColorAtElevation(
                    color = OrbitTheme.colors.surface.main,
                    elevation = if (enabled) elevation else 0.dp,
                ),
                border = null,
                elevation = elevation,
            )
            .padding(8.dp)
            .background(thumbBgColor.value, CircleShape),
    )
}

@Composable
private fun Track(enabled: Boolean, sliderPositions: SliderPositions) {
    val activeTrackColor = when (enabled) {
        true -> OrbitTheme.colors.info.normal
        false -> OrbitTheme.colors.content.disabled.copy(alpha = 0.38f)
    }
    val inactiveTrackColor = when (enabled) {
        true -> OrbitTheme.colors.surface.strong
        false -> OrbitTheme.colors.surface.strong.copy(alpha = 0.38f)
    }
    val activeTickColor = when (enabled) {
        true -> contentColorFor(activeTrackColor).copy(alpha = 0.48f)
        false -> contentColorFor(OrbitTheme.colors.content.disabled).copy(alpha = 0.38f)
    }
    val inactiveTickColor = when (enabled) {
        true -> contentColorFor(inactiveTrackColor).copy(alpha = 0.38f)
        false -> contentColorFor(OrbitTheme.colors.surface.strong).copy(alpha = 0.24f)
    }
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(TrackSize),
    ) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f - 9.9.dp.roundToPx(), center.y)
        val sliderRight = Offset(size.width + 9.9.dp.roundToPx(), center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val dotStart = if (isRtl) Offset(size.width, center.y) else Offset(0f, center.y)
        val dotEnd = if (isRtl) Offset(0f, center.y) else Offset(size.width, center.y)
        val tickSize = TickSize.toPx()
        val trackStrokeWidth = TrackSize.toPx()
        drawLine(
            inactiveTrackColor,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Round,
        )
        val sliderValueEnd = Offset(
            sliderStart.x +
                (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.endInclusive,
            center.y,
        )

        val sliderValueStart = Offset(
            sliderStart.x +
                (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.start,
            center.y,
        )

        drawLine(
            activeTrackColor,
            sliderValueStart,
            sliderValueEnd,
            trackStrokeWidth,
            StrokeCap.Round,
        )
        sliderPositions.tickFractions.groupBy {
            it > sliderPositions.activeRange.endInclusive ||
                it < sliderPositions.activeRange.start
        }.forEach { (outsideFraction, list) ->
            drawPoints(
                list.map {
                    Offset(lerp(dotStart, dotEnd, it).x, center.y)
                },
                PointMode.Points,
                (if (outsideFraction) inactiveTickColor else activeTickColor),
                tickSize,
                StrokeCap.Round,
            )
        }
    }
}

private fun Modifier.sliderSemantics(
    value: Float,
    enabled: Boolean,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
): Modifier {
    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    return semantics {
        if (!enabled) disabled()
        setProgress(
            action = { targetValue ->
                var newValue = targetValue.coerceIn(valueRange.start, valueRange.endInclusive)
                val originalVal = newValue
                val resolvedValue = if (steps > 0) {
                    var distance: Float = newValue
                    for (i in 0..steps + 1) {
                        val stepValue = lerp(
                            valueRange.start,
                            valueRange.endInclusive,
                            i.toFloat() / (steps + 1),
                        )
                        if (abs(stepValue - originalVal) <= distance) {
                            distance = abs(stepValue - originalVal)
                            newValue = stepValue
                        }
                    }
                    newValue
                } else {
                    newValue
                }

                // This is to keep it consistent with AbsSeekbar.java: return false if no
                // change from current.
                if (resolvedValue == coerced) {
                    false
                } else {
                    onValueChange(resolvedValue)
                    onValueChangeFinished?.invoke()
                    true
                }
            },
        )
    }.progressSemantics(value, valueRange, steps)
}

private val TrackSize = 4.dp
private val TickSize = 2.dp

@OrbitPreviews
@Composable
internal fun SliderPreview() {
    Preview {
        var value by remember { mutableStateOf(0.5f) }
        Slider(
            value = value,
            onValueChange = { value = it },
            valueLabel = { Text(it.toString()) },
            startLabel = { Text("0") },
            endLabel = { Text("1") },
        )
        Separator()
        Slider(
            value = value,
            onValueChange = { value = it },
            valueLabel = { Text(it.toString()) },
            startLabel = { Text("Start") },
            endLabel = { Text("End") },
            steps = 3,
        )
        Separator()
        Slider(
            value = value,
            onValueChange = {},
            valueLabel = { Text(it.toString()) },
            startLabel = { Text("Start") },
            endLabel = { Text("End") },
            enabled = false,
            steps = 3,
        )
        Separator()
        Slider(
            value = value,
            onValueChange = {},
            valueLabel = {},
            startLabel = { Text("0") },
            endLabel = { Text("1") },
        )
    }
}
