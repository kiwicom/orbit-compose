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
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.AmbientColors
import kiwi.orbit.icons.AlertCircle
import kiwi.orbit.icons.CheckCircle
import kiwi.orbit.icons.Icons
import kiwi.orbit.icons.InformationCircle
import kiwi.orbit.icons.Visa

@Composable
fun AlertSuccessCard(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.CheckCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    Providers(
        AmbientColors provides colors.copy(
            surfaceBackground = colors.successBg,
            primary = colors.success,
            primaryFg = colors.successFg,
        )
    ) {
        AlertCard(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
fun AlertInfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.InformationCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    Providers(
        AmbientColors provides colors.copy(
            surfaceBackground = colors.secondary.copy(alpha = 0.12f),
            primary = colors.secondary,
            primaryFg = colors.secondaryFg,
        )
    ) {
        AlertCard(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
fun AlertWarningCard(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Visa,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    Providers(
        AmbientColors provides colors.copy(
            surfaceBackground = colors.warningBg,
            primary = colors.warning,
            primaryFg = colors.warningFg,
        )
    ) {
        AlertCard(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
fun AlertCriticalCard(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AlertCircle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = OrbitTheme.colors
    Providers(
        AmbientColors provides colors.copy(
            surfaceBackground = colors.criticalBg,
            primary = colors.critical,
            primaryFg = colors.criticalFg,
        )
    ) {
        AlertCard(
            icon = icon,
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
private fun AlertCard(
    icon: ImageVector,
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
