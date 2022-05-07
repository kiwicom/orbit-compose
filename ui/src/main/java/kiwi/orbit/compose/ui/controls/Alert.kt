package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme
import kotlin.math.roundToInt

@Composable
public fun AlertInfo(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.InformationCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(suppressed),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertSuccess(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.CheckCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(suppressed),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertWarning(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(suppressed),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertCritical(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertOctagon,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(suppressed),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
private fun Alert(
    icon: Painter?,
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val bgColor = OrbitTheme.colors.surface.background
    val borderColor = OrbitTheme.colors.content.subtle.copy(0.08f)
    val accentColor = OrbitTheme.colors.primary.normal
    val shape = OrbitTheme.shapes.normal
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .drawBehind {
                drawRect(bgColor)
                drawLine(
                    color = accentColor,
                    start = Offset.Zero,
                    end = Offset(size.width, 0f),
                    strokeWidth = 6.dp.toPx(), // doubled width to account for path offset
                )
            }
            .border(1.dp, borderColor, shape)
            .padding(
                top = 12.dp + 3.dp, // stroke width
                start = if (icon != null) 12.dp else 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = accentColor,
                )
            }
            AlertContent(
                title = title,
                actions = actions,
                content = content,
            )
        }
    }
}

@Composable
private fun AlertContent(
    title: @Composable ColumnScope.() -> Unit,
    actions: (@Composable () -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        CompositionLocalProvider(
            LocalSmallButtonScope provides true,
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalBold) {
                title()
            }
            content()
            if (actions != null) {
                AlertButtons(Modifier.padding(top = 12.dp), actions) // 16.dp-4.dp
            }
        }
    }
}

@Composable
private fun AlertButtons(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, incomingConstraints ->
        val buttonPadding = 12.dp.toPx().roundToInt()
        val buttonsCount = measurables.size
        val buttonSize =
            ((incomingConstraints.maxWidth - (buttonPadding * (buttonsCount - 1))) / buttonsCount)
        val buttonConstraint =
            incomingConstraints.copy(minWidth = buttonSize, maxWidth = buttonSize)

        val placeables = measurables.map {
            it.measure(buttonConstraint)
        }

        val maxHeight = placeables.maxOfOrNull { it.height } ?: 0
        layout(incomingConstraints.maxWidth, maxHeight) {
            var x = 0
            for (placeable in placeables) {
                placeable.place(x, y = 0)
                x += buttonSize + buttonPadding
            }
        }
    }
}
