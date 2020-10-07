package kiwi.orbit.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonConstants
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.contentColorFor
import androidx.compose.material.Button as MaterialButton
import androidx.compose.material.TextButton as MaterialTextButton

@Composable
private fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionState: InteractionState = remember { InteractionState() },
    elevation: Dp = ButtonConstants.animateDefaultElevation(interactionState, enabled),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    backgroundColor: Color = if (enabled) OrbitTheme.colors.primary else OrbitTheme.colors.surfaceDisabled,
    contentColor: Color = if (enabled) contentColorFor(backgroundColor) else ButtonConstants.defaultDisabledContentColor,
    contentPadding: PaddingValues = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = if (enabled) OrbitTheme.colors.primary else OrbitTheme.colors.surfaceDisabled
    )
}

@Composable
fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = if (enabled) OrbitTheme.colors.primaryBg else OrbitTheme.colors.surfaceDisabled,
        elevation = 1.dp
    )
}

@Composable
fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = if (enabled) OrbitTheme.colors.surfaceSecondary else OrbitTheme.colors.surfaceDisabled,
        elevation = 1.dp
    )
}

@Composable
fun ButtonLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonConstants.DefaultTextContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MaterialTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = 0.dp,
        shape = MaterialTheme.shapes.small,
        border = null,
        backgroundColor = Color.Transparent,
        contentColor = if (enabled) OrbitTheme.colors.primary else ButtonConstants.defaultDisabledContentColor,
        contentPadding = contentPadding,
        content = content
    )
}
