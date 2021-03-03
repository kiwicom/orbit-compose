package kiwi.orbit.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.LocalColors
import kiwi.orbit.foundation.withCritical
import kiwi.orbit.foundation.withInteractive
import kiwi.orbit.foundation.withSuccess
import kiwi.orbit.foundation.withWarning

@Composable
public fun BadgeNeutral(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            primary = surfaceContent,
            primaryContent = surface,
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = false, icon, content)
    }
}

@Composable
public fun BadgeNeutralSubtle(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            primary = surfaceContent,
            primaryAltSubtle = surfaceAlt,
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
public fun BadgeSecondary(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            surface = surfaceBackground,
            primary = surfaceContent,
            primaryAltSubtle = surfaceAlt,
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
public fun BadgeInfo(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withInteractive(),
    ) {
        Badge(subtle = false, icon, content)
    }
}

@Composable
public fun BadgeInfoSubtle(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withInteractive(),
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
public fun BadgeSuccess(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withSuccess(),
    ) {
        Badge(subtle = false, icon, content)
    }
}

@Composable
public fun BadgeSuccessSubtle(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withSuccess(),
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
public fun BadgeWarning(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withWarning(),
    ) {
        Badge(subtle = false, icon, content)
    }
}

@Composable
public fun BadgeWarningSubtle(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withWarning(),
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
public fun BadgeCritical(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withCritical(),
    ) {
        Badge(subtle = false, icon, content)
    }
}

@Composable
public fun BadgeCriticalSubtle(
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withCritical(),
    ) {
        Badge(subtle = true, icon, content)
    }
}

@Composable
private fun Badge(
    subtle: Boolean,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val shape = RoundedCornerShape(percent = 50)
    val modifier = if (subtle) {
        Modifier
            .border(1.dp, OrbitTheme.colors.primaryAltSubtle, shape)
            .background(OrbitTheme.colors.surface, shape)
    } else {
        Modifier
            .background(OrbitTheme.colors.primary, shape)

    }
    val contentColor = if (subtle) {
        OrbitTheme.colors.primary
    } else {
        OrbitTheme.colors.primaryContent
    }
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Row(
            Modifier
                .defaultMinSize(minHeight = 32.dp)
                .then(modifier)
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Box(Modifier.size(20.dp)) {
                    icon()
                }
            }
            content()
        }
    }
}
