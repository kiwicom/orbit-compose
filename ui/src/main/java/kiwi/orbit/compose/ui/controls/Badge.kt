package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInteractiveTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme

@Composable
public fun BadgeNeutral(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            primary = primary.copy(
                main = this.content.normal,
                onMain = surface.main,
            )
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
            surface = surface.copy(background = surface.main),
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
            surface = surface.copy(main = surface.background),
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
        LocalColors provides OrbitTheme.colors.asInteractiveTheme(),
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
        LocalColors provides OrbitTheme.colors.asInteractiveTheme(),
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
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
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
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
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
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
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
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
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
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
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
        ProvideMergedTextStyle(OrbitTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)) {
            content()
        }
    }
}
