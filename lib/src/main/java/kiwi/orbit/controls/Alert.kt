package kiwi.orbit.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.LocalColors
import kiwi.orbit.foundation.LocalElevationEnabled
import kiwi.orbit.icons.Icons


@Composable
public fun AlertInfo(
    modifier: Modifier = Modifier,
    icon: Painter = Icons.InformationCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    CompositionLocalProvider(
        LocalColors provides colors.copy(
            surfaceBackground = colors.interactiveSubtle,
            primary = colors.interactive,
            primaryContent = colors.interactiveContent,
            primarySubtle = colors.interactiveAltSubtle,
        ),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertSuccess(
    modifier: Modifier = Modifier,
    icon: Painter = Icons.CheckCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    CompositionLocalProvider(
        LocalColors provides colors.copy(
            surfaceBackground = colors.successSubtle,
            primary = colors.success,
            primaryContent = colors.successContent,
            primarySubtle = colors.successAltSubtle
        ),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertWarning(
    modifier: Modifier = Modifier,
    icon: Painter = Icons.Visa,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    CompositionLocalProvider(
        LocalColors provides colors.copy(
            surfaceBackground = colors.warningSubtle,
            primary = colors.warning,
            primaryContent = colors.warningContent,
            primarySubtle = colors.warningAltSubtle,
        ),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
public fun AlertCritical(
    modifier: Modifier = Modifier,
    icon: Painter = Icons.AlertCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    CompositionLocalProvider(
        LocalColors provides colors.copy(
            surfaceBackground = colors.criticalSubtle,
            primary = colors.critical,
            primaryContent = colors.criticalContent,
            primarySubtle = colors.criticalAltSubtle,
        ),
        LocalElevationEnabled provides false,
    ) {
        Alert(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
private fun Alert(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier
            .border(1.dp, OrbitTheme.colors.primary, MaterialTheme.shapes.medium)
            .background(OrbitTheme.colors.surfaceBackground, MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {
        Row {
            Icon(icon, contentDescription = null, tint = OrbitTheme.colors.primary)
            Spacer(Modifier.width(8.dp))
            Column(content = content)
        }
    }
}
