package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme

/**
 * Cloud colored Badge.
 */
@Composable
public fun BadgeNeutral(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            surface = surface.copy(main = surface.background),
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

/**
 * White colored Badge.
 */
@Composable
public fun BadgeNeutralSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            surface = surface.copy(background = surface.main),
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

/**
 * Black colored Badge.
 */
@Composable
public fun BadgeNeutralStrong(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            primary = primary.copy(
                normal = this.content.normal,
                onNormal = surface.main,
            )
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

/**
 * Blue normal colored Badge.
 */
@Composable
public fun BadgeInfo(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

/**
 * Blue light colored Badge.
 */
@Composable
public fun BadgeInfoSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

/**
 * Green normal colored Badge.
 */
@Composable
public fun BadgeSuccess(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

/**
 * Green light colored Badge.
 */
@Composable
public fun BadgeSuccessSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

/**
 * Orange normal colored Badge.
 */
@Composable
public fun BadgeWarning(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

/**
 * Orange light colored Badge.
 */
@Composable
public fun BadgeWarningSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

/**
 * Red normal colored Badge.
 */
@Composable
public fun BadgeCritical(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

/**
 * Red light colored Badge.
 */
@Composable
public fun BadgeCriticalSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
private fun Badge(
    subtle: Boolean,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    ThemedSurface(
        modifier = modifier,
        subtle = subtle,
        shape = RoundedCornerShape(percent = 50),
        strokeWidth = 0.5.dp,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodySmallMedium) {
            icon?.invoke()
            content()
        }
    }
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
    }
}
