package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.layout.size

public enum class TimelineItemStatus {
    PastSuccess,
    PastWarning,
    PastCritical,
    InProgress,
    CurrentSuccess,
    CurrentWarning,
    CurrentCritical,
    Future,
}

public object TimelineScope

/**
 * Timeline container.
 *
 * Use [TimelineItem] composable to render particular steps.
 *
 * ```
 * Timeline {
 *     TimelineItem(TimelineItemStatus.PastSuccess) { Text("Step 1") }
 *     TimelineItem(TimelineItemStatus.InProgress) { Text("Step 2") }
 *     TimelineItem(TimelineItemStatus.Future) { Text("Step 3") }
 * }
 * ```
 *
 * The "active" state is modelled by a Current* or InProgress state.
 */
@Composable
public fun Timeline(
    modifier: Modifier = Modifier,
    content: @Composable TimelineScope.() -> Unit,
) {
    Layout(
        content = {
            TimelineScope.content()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        /**
         * Timeline expects the measurables in the following order:
         * 1. item 0
         * 3. line for item 0
         * 2. line gradient above item 0
         * 4. item 1
         * 6. line for item 1
         * 5. line gradient above item 1
         * ...
         */
        check(measurables.size.mod(3) == 0) {
            "Timeline container can render only TimelineItem composables."
        }

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val verticalPadding = 16.dp.roundToPx()
        val iconHeight = 12.sp.roundToPx()

        val items = mutableListOf<Placeable>()
        val lines = mutableListOf<Placeable>()
        val gradients = mutableListOf<Placeable>()

        for (i in measurables.indices) {
            val measurable = measurables[i]
            when (i.mod(3)) {
                0 -> items.add(measurable.measure(looseConstraints))
                1 -> {
                    val lineHeight = (items.last().height + verticalPadding - iconHeight).coerceAtLeast(0)
                    val placeable = measurable.measure(Constraints.fixedHeight(lineHeight))
                    lines.add(placeable)
                }
                2 -> {
                    val previousLine = lines.getOrNull(lines.lastIndex - 1)
                    if (previousLine != null) {
                        val placeable = measurable.measure(Constraints.fixedHeight(previousLine.height))
                        gradients.add(placeable)
                    }
                }
            }
        }

        /**
         * Do not lay out last line and above first item gradient.
         * The gradient above the first item is already null as cannot be measured.
         * Drop the last line only.
         */
        lines.removeLast()
        check(lines.size == gradients.size) {
            "Timeline container can render only TimelineItem composables."
        }

        val iconTopOffset = 16.sp.roundToPx()
        val height = items.sumOf { it.height } + ((items.count() - 1) * verticalPadding)
        val width = items.maxOf { it.width }

        layout(width, height) {
            var y = 0
            items.forEach { placeable ->
                placeable.placeRelative(0, y)
                y += placeable.height + verticalPadding
            }
            y = iconTopOffset
            for (i in lines.indices) {
                val line = lines[i]
                line.placeRelative(0, y)
                gradients[i].placeRelative(0, y)
                y += line.height + iconHeight
            }
        }
    }
}

/**
 * Timeline item.
 *
 * Always use directly inside the [Timeline] container.
 */
@Composable
public fun TimelineScope.TimelineItem(
    status: TimelineItemStatus,
    modifier: Modifier = Modifier,
    description: @Composable () -> Unit = {},
    title: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ItemIcon(status)
        ItemContent(status, title, description)
    }
    ItemLine(status)
    ItemTopLineGradient(status)
}

@Composable
private fun ItemIcon(
    status: TimelineItemStatus,
) {
    when (status) {
        TimelineItemStatus.PastSuccess -> ItemIcon(Icons.CheckCircle, status.color)
        TimelineItemStatus.PastWarning -> ItemIcon(Icons.AlertCircle, status.color)
        TimelineItemStatus.PastCritical -> ItemIcon(Icons.CloseCircle, status.color)
        TimelineItemStatus.InProgress -> ItemInProgressIcon()
        TimelineItemStatus.CurrentSuccess -> ItemIcon(Icons.CheckCircle, status.color)
        TimelineItemStatus.CurrentWarning -> ItemIcon(Icons.AlertCircle, status.color)
        TimelineItemStatus.CurrentCritical -> ItemIcon(Icons.CloseCircle, status.color)
        TimelineItemStatus.Future -> ItemFutureIcon()
    }
}

@Composable
private fun ItemFutureIcon() {
    Box(
        Modifier
            .size(20.sp)
            .padding(4.dp)
            .border(2.dp, TimelineItemStatus.Future.color, CircleShape),
    )
}

@Composable
private fun ItemInProgressIcon() {
    val color = TimelineItemStatus.InProgress.color
    val anim by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    Canvas(Modifier.size(20.sp)) {
        val twoDp = 2.sp.toPx()
        drawCircle(color, size.width / 2 - twoDp, alpha = 0.1f, style = Stroke(twoDp + twoDp * anim)) // glow
        drawCircle(color, size.width / 2 - 2 * twoDp, style = Stroke(twoDp)) // circle
        drawCircle(color, twoDp * anim, alpha = anim) // dot
    }
}

@Composable
private fun ItemIcon(
    painter: Painter,
    color: Color,
) {
    Icon(
        painter = painter,
        contentDescription = null,
        tint = color,
        modifier = Modifier
            .size(20.sp)
            .background(color.copy(alpha = 0.1f), shape = CircleShape)
            .padding(2.dp),
    )
}

@Composable
private fun ItemContent(
    status: TimelineItemStatus,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val emphasis = when (status) {
            TimelineItemStatus.InProgress -> ContentEmphasis.Normal
            TimelineItemStatus.CurrentSuccess -> ContentEmphasis.Normal
            TimelineItemStatus.CurrentWarning -> ContentEmphasis.Normal
            TimelineItemStatus.CurrentCritical -> ContentEmphasis.Normal
            else -> ContentEmphasis.Subtle
        }
        ProvideContentEmphasis(emphasis) {
            ProvideMergedTextStyle(OrbitTheme.typography.title5) {
                title()
            }
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                description()
            }
        }
    }
}

@Composable
private fun ItemLine(
    status: TimelineItemStatus,
) {
    val width = with(LocalDensity.current) {
        20.sp.toDp()
    }
    if (status in setOf(
            TimelineItemStatus.InProgress,
            TimelineItemStatus.CurrentSuccess,
            TimelineItemStatus.CurrentWarning,
            TimelineItemStatus.CurrentCritical,
            TimelineItemStatus.Future,
        )
    ) {
        val color = OrbitTheme.colors.surface.normalAlt
        Canvas(modifier = Modifier.width(width)) {
            drawLine(
                color = color,
                strokeWidth = 2.dp.toPx(),
                start = Offset(size.width / 2, 2.sp.toPx()),
                end = Offset(size.width / 2, size.height),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(2.dp.toPx(), 6.dp.toPx())),
                cap = StrokeCap.Round,
            )
        }
    } else {
        val color = status.color
        Canvas(modifier = Modifier.width(width)) {
            drawLine(
                color = color,
                strokeWidth = 2.dp.toPx(),
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
            )
        }
    }
}

@Composable
private fun ItemTopLineGradient(
    status: TimelineItemStatus,
) {
    if (status in setOf(
            TimelineItemStatus.PastSuccess,
            TimelineItemStatus.InProgress,
            TimelineItemStatus.CurrentSuccess,
            TimelineItemStatus.Future,
        )
    ) {
        Box {}
        return
    }

    val width = with(LocalDensity.current) {
        20.sp.toDp()
    }
    val color = status.color
    val gradient = Brush.linearGradient(colors = listOf(Color.Transparent, color))
    Canvas(modifier = Modifier.width(width)) {
        drawLine(
            brush = gradient,
            strokeWidth = 2.dp.toPx(),
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
        )
    }
}

private val TimelineItemStatus.color: Color
    @Composable
    get() = when (this) {
        TimelineItemStatus.PastSuccess -> OrbitTheme.colors.success.normal
        TimelineItemStatus.PastWarning -> OrbitTheme.colors.warning.normal
        TimelineItemStatus.PastCritical -> OrbitTheme.colors.critical.normal
        TimelineItemStatus.InProgress -> OrbitTheme.colors.success.normal
        TimelineItemStatus.CurrentSuccess -> OrbitTheme.colors.success.normal
        TimelineItemStatus.CurrentWarning -> OrbitTheme.colors.warning.normal
        TimelineItemStatus.CurrentCritical -> OrbitTheme.colors.critical.normal
        TimelineItemStatus.Future -> OrbitTheme.colors.surface.normalAlt
    }

@OrbitPreviews
@Composable
internal fun TimelinePreview() {
    Preview {
        Timeline {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
                description = { Text("To check you in, we need your passport/ID or some other details at least 3h before departure.") },
            )
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Waiting for the airline") },
                description = { Text("We'll wait for the airline to open their online check-in.") },
            )
            TimelineItem(
                status = TimelineItemStatus.InProgress,
                title = { Text("Processing online check-in") },
                description = { Text("We'll check you in with the airport and notify you when it’s finished.") },
            )
            TimelineItem(
                status = TimelineItemStatus.Future,
                title = { Text("Boarding passes ready") },
                description = { Text("You'll find your boarding passes here as soon as they’re ready at least 2h before your departure.") },
            )
        }
    }
}

@OrbitPreviews
@Composable
internal fun TimelineStatesPreview() {
    Preview {
        Timeline {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
            )
            TimelineItem(
                status = TimelineItemStatus.CurrentWarning,
                title = { Text("Waiting for the airline") },
            )
        }
        Timeline {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
            )
            TimelineItem(
                status = TimelineItemStatus.CurrentCritical,
                title = { Text("Waiting for the airline") },
            )
        }
        Timeline {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
            )
            TimelineItem(
                status = TimelineItemStatus.PastCritical,
                title = { Text("Waiting for the airline") },
            )
            TimelineItem(
                status = TimelineItemStatus.PastWarning,
                title = { Text("Waiting for the airline even more") },
            )
            TimelineItem(
                status = TimelineItemStatus.CurrentCritical,
                title = { Text("Waiting for the airline, the last one") },
            )
        }
    }
}
