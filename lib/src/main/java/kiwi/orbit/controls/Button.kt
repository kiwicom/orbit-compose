package kiwi.orbit.controls

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.ButtonColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.contentColorFor
import androidx.compose.material.Button as MaterialButton
import androidx.compose.material.ButtonDefaults as MaterialButtonDefaults
import androidx.compose.material.TextButton as MaterialTextButton

@Composable
public fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val elevation = if (OrbitTheme.elevationEnabled) {
        MaterialButtonDefaults.elevation()
    } else {
        MaterialButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    }
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = OrbitTheme.colors.primary,
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val elevation = if (OrbitTheme.elevationEnabled) {
        MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 4.dp,
        )
    } else {
        MaterialButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    }
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = OrbitTheme.colors.primarySubtle,
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val elevation = if (OrbitTheme.elevationEnabled) {
        MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
        )
    } else {
        MaterialButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    }
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = OrbitTheme.colors.surfaceAlt,
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonCritical(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val elevation = if (OrbitTheme.elevationEnabled) {
        MaterialButtonDefaults.elevation()
    } else {
        MaterialButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    }
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = OrbitTheme.colors.critical,
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonCriticalSubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val elevation = if (OrbitTheme.elevationEnabled) {
        MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 4.dp,
        )
    } else {
        MaterialButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    }
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = OrbitTheme.colors.criticalSubtle,
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MaterialTextButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = ButtonMinHeight),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = OrbitTheme.colors.primary,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

internal object ButtonDefaults {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun buttonColors(
        backgroundColor: Color = OrbitTheme.colors.primary,
        contentColor: Color = contentColorFor(backgroundColor),
        disabledContentColor: Color = contentColor.copy(alpha = ContentAlpha.disabled)
    ): ButtonColors = MaterialButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
    )
}

private val ButtonMinHeight = 44.dp
