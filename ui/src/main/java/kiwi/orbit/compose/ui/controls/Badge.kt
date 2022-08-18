package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.Colors
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asNeutralSubtleStrongTheme
import kiwi.orbit.compose.ui.foundation.asNeutralTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme
import kiwi.orbit.compose.ui.foundation.contentColorFor

/**
 * Cloud colored Badge.
 */
@Composable
public fun BadgeNeutral(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asNeutralTheme(), subtle = true, modifier, icon, content)
}

/**
 * White colored Badge.
 */
@Composable
public fun BadgeNeutralSubtle(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asNeutralSubtleStrongTheme(), subtle = true, modifier, icon, content)
}

/**
 * Black colored Badge.
 */
@Composable
public fun BadgeNeutralStrong(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asNeutralSubtleStrongTheme(), subtle = false, modifier, icon, content)
}

/**
 * Blue normal colored Badge.
 */
@Composable
public fun BadgeInfo(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asInfoTheme(), subtle = false, modifier, icon, content)
}

/**
 * Blue light colored Badge.
 */
@Composable
public fun BadgeInfoSubtle(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asInfoTheme(), subtle = true, modifier, icon, content)
}

/**
 * Green normal colored Badge.
 */
@Composable
public fun BadgeSuccess(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asSuccessTheme(), subtle = false, modifier, icon, content)
}

/**
 * Green light colored Badge.
 */
@Composable
public fun BadgeSuccessSubtle(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asSuccessTheme(), subtle = true, modifier, icon, content)
}

/**
 * Orange normal colored Badge.
 */
@Composable
public fun BadgeWarning(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asWarningTheme(), subtle = false, modifier, icon, content)
}

/**
 * Orange light colored Badge.
 */
@Composable
public fun BadgeWarningSubtle(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asWarningTheme(), subtle = true, modifier, icon, content)
}

/**
 * Red normal colored Badge.
 */
@Composable
public fun BadgeCritical(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asCriticalTheme(), subtle = false, modifier, icon, content)
}

/**
 * Red light colored Badge.
 */
@Composable
public fun BadgeCriticalSubtle(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Badge(LocalColors.current.asCriticalTheme(), subtle = true, modifier, icon, content)
}

@Composable
public fun BadgeBundleBasic(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit = {},
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
    icon: @Composable RowScope.() -> Unit = {},
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
    icon: @Composable RowScope.() -> Unit = {},
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

@Composable
private fun Badge(
    colors: Colors,
    subtle: Boolean,
    modifier: Modifier,
    icon: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalColors provides colors) {
        ThemedSurface(
            subtle = subtle,
            shape = BadgeShape,
            modifier = modifier,
            borderStrokeWidth = BadgeStrokeWidth,
            contentPadding = BadgeContentPadding,
            horizontalArrangement = BadgeArrangement,
            verticalAlignment = BadgeAlignment,
            content = {
                ProvideMergedTextStyle(OrbitTheme.typography.bodySmallMedium) {
                    icon()
                    content()
                }
            },
        )
    }
}

@OrbitPreviews
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
            icon = { Icon(Icons.Android, contentDescription = null) },
        ) { Text("Text") }
        BadgeInfo(
            icon = { Icon(Icons.Android, contentDescription = null) },
        ) { Text("Text") }
        BadgeBundleBasic(
            icon = { Icon(Icons.Check, contentDescription = null) },
        ) {
            Text("Text")
        }
        BadgeBundleMedium(
            icon = { Icon(Icons.Check, contentDescription = null) },
        ) {
            Text("Text")
        }
        BadgeBundleTop(
            icon = { Icon(Icons.Check, contentDescription = null) },
        ) {
            Text("Text")
        }
    }
}
