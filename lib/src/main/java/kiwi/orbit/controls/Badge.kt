package kiwi.orbit.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.components.ThemedSurface
import kiwi.orbit.foundation.LocalColors
import kiwi.orbit.foundation.withCritical
import kiwi.orbit.foundation.withInteractive
import kiwi.orbit.foundation.withSuccess
import kiwi.orbit.foundation.withWarning

@Composable
public fun BadgeNeutral(
    modifier: Modifier = Modifier,
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
        Badge(subtle = false, modifier, icon, content)
    }
}

@Composable
public fun BadgeNeutralSubtle(
    modifier: Modifier = Modifier,
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
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
public fun BadgeSecondary(
    modifier: Modifier = Modifier,
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
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
public fun BadgeInfo(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withInteractive(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

@Composable
public fun BadgeInfoSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withInteractive(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
public fun BadgeSuccess(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withSuccess(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

@Composable
public fun BadgeSuccessSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withSuccess(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
public fun BadgeWarning(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withWarning(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

@Composable
public fun BadgeWarningSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withWarning(),
    ) {
        Badge(subtle = true, modifier, icon, content)
    }
}

@Composable
public fun BadgeCritical(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withCritical(),
    ) {
        Badge(subtle = false, modifier, icon, content)
    }
}

@Composable
public fun BadgeCriticalSubtle(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.withCritical(),
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
        subtle = subtle,
        shape = RoundedCornerShape(percent = 50),
        modifier = modifier.requiredHeight(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 8.dp),
    ) {
        if (icon != null) {
            Box(Modifier.size(20.dp)) {
                icon()
            }
        }
        ProvideTextStyle(OrbitTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)) {
            content()
        }
    }
}
