package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme

@Composable
public fun AlertInlineInfo(
    title: @Composable () -> Unit,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.InformationCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineSuccess(
    title: @Composable () -> Unit,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.CheckCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineWarning(
    title: @Composable () -> Unit,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertCircle,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
public fun AlertInlineCritical(
    title: @Composable () -> Unit,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    suppressed: Boolean = false,
    icon: Painter? = Icons.AlertOctagon,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        AlertInline(
            icon = icon,
            title = title,
            action = action,
            suppressed = suppressed,
            modifier = modifier,
        )
    }
}

@Composable
private fun AlertInline(
    icon: Painter?,
    title: @Composable () -> Unit,
    action: @Composable () -> Unit,
    suppressed: Boolean,
    modifier: Modifier = Modifier,
) {
    AlertContainer(
        suppressed = suppressed,
        contentPadding = PaddingValues(
            top = 14.dp - 3.dp,
            bottom = 14.dp,
            start = 12.dp,
            end = 12.dp,
        ),
        modifier = modifier,
    ) {
        Row {
            ProvideMergedTextStyle(OrbitTheme.typography.title5) {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp).alignByBaseline(),
                        tint = OrbitTheme.colors.primary.normal,
                    )
                } else {
                    Spacer(Modifier.width(4.dp))
                }
                Box(Modifier.weight(1f).alignByBaseline()) {
                    title()
                }
            }
            Spacer(Modifier.width(8.dp))
            Box(Modifier.alignByBaseline()) {
                action()
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun AlertInlinePreview() {
    Preview {
        AlertInlineInfo(
            title = { Text("Title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
        AlertInlineSuccess(
            title = { Text("Title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
        AlertInlineWarning(
            title = { Text("Title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
        AlertInlineCritical(
            title = { Text("Title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
    }
}

@OrbitPreviews
@Composable
internal fun AlertInlineCustomizedPreview() {
    Preview {
        AlertInlineInfo(
            title = { Text("Very long title with multiple words and characters") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
        AlertInlineInfo(
            icon = null,
            title = { Text("Very long title with multiple words and characters") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        )
    }
}
