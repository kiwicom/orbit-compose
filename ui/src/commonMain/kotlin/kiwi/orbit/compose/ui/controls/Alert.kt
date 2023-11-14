package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.LocalRoundedContainerScope
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun AlertInfo(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.InformationCircle,
    actions: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            actions = actions,
            content = content,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertSuccess(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.CheckCircle,
    actions: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            actions = actions,
            content = content,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertWarning(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertCircle,
    actions: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            actions = actions,
            content = content,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertCritical(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertOctagon,
    actions: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            actions = actions,
            content = content,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
private fun Alert(
    icon: Painter?,
    title: @Composable ColumnScope.() -> Unit,
    actions: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    suppressed: Boolean,
    modifier: Modifier = Modifier,
) {
    AlertContainer(
        suppressed = suppressed,
        contentPadding = PaddingValues(12.dp),
        modifier = modifier,
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = OrbitTheme.colors.primary.normal,
                )
            }
            AlertContent(
                title = title,
                actions = actions,
                content = content,
                suppressed = suppressed,
            )
        }
    }
}

@Composable
private fun AlertContent(
    title: @Composable ColumnScope.() -> Unit,
    actions: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    suppressed: Boolean,
) {
    Column {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            ProvideMergedTextStyle(OrbitTheme.typography.title5) {
                title()
            }
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                content()
            }
        }
        AlertButtons(topPadding = 12.dp, suppressed, actions)
    }
}

@Composable
private fun AlertButtons(
    topPadding: Dp,
    suppressed: Boolean,
    content: @Composable () -> Unit,
) {
    val backgroundColor = when (suppressed) {
        true -> OrbitTheme.colors.content.subtle.copy(alpha = 0.1f)
        false -> OrbitTheme.colors.primary.normal.copy(alpha = 0.1f)
    }
    val contentColor = when (suppressed) {
        true -> contentColorFor(OrbitTheme.colors.surface.normal)
        false -> contentColorFor(OrbitTheme.colors.primary.subtle)
    }
    val colors = OrbitTheme.colors.copy(
        surface = OrbitTheme.colors.surface.copy(
            normal = backgroundColor,
        ),
        content = OrbitTheme.colors.content.copy(
            normal = contentColor,
        ),
    )
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalSmallButtonScope provides true,
        LocalRoundedContainerScope provides true,
    ) {
        AlertButtonsLayout(topPadding, content)
    }
}

@Composable
private fun AlertButtonsLayout(
    topPadding: Dp,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
    ) { measurables, incomingConstraints ->
        if (measurables.isEmpty()) {
            return@Layout layout(0, 0) {}
        }

        val topPaddingPx = topPadding.roundToPx()
        val buttonSpacing = 8.dp.roundToPx()

        val buttonsCount = measurables.size
        val buttonSize =
            ((incomingConstraints.maxWidth - (buttonSpacing * (buttonsCount - 1))) / buttonsCount)
        val buttonConstraint =
            incomingConstraints.copy(minWidth = buttonSize, maxWidth = buttonSize)

        val placeables = measurables.map {
            it.measure(buttonConstraint)
        }

        val maxHeight = placeables.maxOf { it.height } + topPaddingPx
        layout(incomingConstraints.maxWidth, maxHeight) {
            var x = 0
            for (placeable in placeables) {
                placeable.place(x, y = topPaddingPx)
                x += buttonSize + buttonSpacing
            }
        }
    }
}

@Composable
internal fun AlertContainer(
    suppressed: Boolean,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val bgColor = when (suppressed) {
        true -> OrbitTheme.colors.surface.subtle
        false -> OrbitTheme.colors.primary.subtle
    }
    val borderColor = when (suppressed) {
        true -> OrbitTheme.colors.content.subtle
        false -> OrbitTheme.colors.primary.normal
    }.copy(0.1f)
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
            .padding(top = 3.dp) // stroke width
            .padding(contentPadding),
        content = content,
    )
}

@OrbitPreviews
@Composable
internal fun AlertInfoPreview() {
    Preview {
        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = {
                Text(
                    buildAnnotatedString {
                        append(
                            "To avoid boarding complications, your entire name must be entered " +
                                "exactly as it appears in your passport/ID. ",
                        )
                        withStyle(
                            SpanStyle(
                                color = OrbitTheme.colors.content.highlight,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.Underline,
                            ),
                        ) {
                            append("Some link")
                        }
                        append(".")
                    },
                )
            },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("More info")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
        )
    }
}

@OrbitPreviews
@Composable
internal fun AlertSuccessPreview() {
    Preview {
        AlertSuccess(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
        )
        AlertSuccess(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
            suppressed = true,
        )
    }
}

@OrbitPreviews
@Composable
internal fun AlertWarningPreview() {
    Preview {
        AlertWarning(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
        )
        AlertWarning(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
            suppressed = true,
        )
    }
}

@OrbitPreviews
@Composable
internal fun AlertCriticalPreview() {
    Preview {
        AlertCritical(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
        )
        AlertCritical(
            title = { Text("Title") },
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
                ButtonSecondary(onClick = {}) { Text("Secondary") }
            },
            suppressed = true,
        )
    }
}

@OrbitPreviews
@Composable
internal fun AlertSimplePreview() {
    Preview {
        AlertInfo(
            title = { Text("Title") },
            content = {},
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
            },
        )
        AlertInfo(
            title = {},
            content = { Text("Content description") },
        )
        AlertInfo(
            title = { Text("Title") },
            content = {},
        )
        AlertInfo(
            icon = null,
            title = {},
            content = { Text("Content description") },
        )
        AlertInfo(
            icon = null,
            title = {},
            content = { Text("Content description") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Primary") }
            },
        )
    }
}
