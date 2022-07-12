package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.contentColorFor

/**
 * Cloud colored Badge.
 */
@Composable
public fun BadgeNeutral(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.surface.background,
        borderColor = OrbitTheme.colors.surface.strong,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * White colored Badge.
 */
@Composable
public fun BadgeNeutralSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.surface.main,
        borderColor = OrbitTheme.colors.surface.strong,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Black colored Badge.
 */
@Composable
public fun BadgeNeutralStrong(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.content.normal,
        contentColor = OrbitTheme.colors.surface.main,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Blue normal colored Badge.
 */
@Composable
public fun BadgeInfo(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.info.normal,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Blue light colored Badge.
 */
@Composable
public fun BadgeInfoSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.info.subtle,
        borderColor = OrbitTheme.colors.info.subtleAlt,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Green normal colored Badge.
 */
@Composable
public fun BadgeSuccess(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.success.normal,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Green light colored Badge.
 */
@Composable
public fun BadgeSuccessSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.success.subtle,
        borderColor = OrbitTheme.colors.success.subtleAlt,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Orange normal colored Badge.
 */
@Composable
public fun BadgeWarning(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.warning.normal,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Orange light colored Badge.
 */
@Composable
public fun BadgeWarningSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.warning.subtle,
        borderColor = OrbitTheme.colors.warning.subtleAlt,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Red normal colored Badge.
 */
@Composable
public fun BadgeCritical(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.critical.normal,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

/**
 * Red light colored Badge.
 */
@Composable
public fun BadgeCriticalSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = OrbitTheme.colors.critical.subtle,
        borderColor = OrbitTheme.colors.critical.subtleAlt,
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

@Composable
public fun BadgeBundleBasic(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.basicGradient,
        contentColor = contentColorFor(OrbitTheme.colors.bundle.basicGradient),
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

@Composable
public fun BadgeBundleMedium(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.mediumGradient,
        contentColor = contentColorFor(OrbitTheme.colors.bundle.mediumGradient),
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

@Composable
public fun BadgeBundleTop(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    BadgePrimitive(
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.topGradient,
        contentColor = contentColorFor(OrbitTheme.colors.bundle.topGradient),
        modifier = modifier,
        icon = icon,
        content = content,
    )
}

@Preview
@Composable
internal fun BadgePreview() {
    Preview {
        Row {
            BadgeInfo { Text("Text") }
            BadgeInfoSubtle { Text("Text") }
        }
        Row {
            BadgeSuccess { Text("Text") }
            BadgeSuccessSubtle { Text("Text") }
        }
        Row {
            BadgeWarning { Text("Text") }
            BadgeWarningSubtle { Text("Text") }
        }
        Row {
            BadgeCritical { Text("Text") }
            BadgeCriticalSubtle { Text("Text") }
        }
        Row {
            BadgeNeutralStrong { Text("Text") }
            BadgeNeutralSubtle { Text("Text") }
        }
        BadgeNeutral(
            icon = { Icon(Icons.Android, contentDescription = null) }
        ) { Text("Text") }
        BadgeInfo(
            icon = { Icon(Icons.Android, contentDescription = null) }
        ) { Text("Text") }
        BadgeBundleBasic(
            icon = { Icon(Icons.Check, contentDescription = null) }
        ) {
            Text("Text")
        }
        BadgeBundleMedium(
            icon = { Icon(Icons.Check, contentDescription = null) }
        ) {
            Text("Text")
        }
        BadgeBundleTop(
            icon = { Icon(Icons.Check, contentDescription = null) }
        ) {
            Text("Text")
        }
    }
}
