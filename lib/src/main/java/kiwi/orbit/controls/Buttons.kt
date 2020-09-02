package kiwi.orbit.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.InnerPadding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonConstants
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
internal fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp = 2.dp,
    disabledElevation: Dp = 0.dp,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    backgroundColor: Color = OrbitTheme.colors.primary,
    disabledBackgroundColor: Color = ButtonConstants.defaultDisabledBackgroundColor,
    contentColor: Color = contentColorFor(backgroundColor),
    disabledContentColor: Color = ButtonConstants.defaultDisabledContentColor,
    contentPadding: InnerPadding = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        disabledElevation = disabledElevation,
        shape = shape,
        border = border,
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: InnerPadding = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = OrbitTheme.colors.primary
    )
}

@Composable
fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: InnerPadding = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = OrbitTheme.colors.primaryBg,
        elevation = 1.dp
    )
}

@Composable
fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: InnerPadding = ButtonConstants.DefaultContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content,
        backgroundColor = OrbitTheme.colors.surfaceSecondary,
        elevation = 1.dp
    )
}

@Composable
fun ButtonLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: InnerPadding = ButtonConstants.DefaultTextContentPadding,
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
        contentColor = OrbitTheme.colors.primary,
        disabledContentColor = ButtonConstants.defaultDisabledContentColor,
        contentPadding = contentPadding,
        content = content
    )
}
