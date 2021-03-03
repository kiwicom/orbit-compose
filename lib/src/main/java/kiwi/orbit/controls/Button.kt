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
import androidx.compose.ui.graphics.compositeOver
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
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 44.dp),
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = OrbitTheme.colors.primary,
        ),
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
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 44.dp),
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = OrbitTheme.colors.primarySubtle,
        ),
        elevation = MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 4.dp,
        ),
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
    MaterialButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 44.dp),
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = OrbitTheme.colors.surfaceAlt,
        ),
        elevation = MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
        ),
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
        modifier = modifier.defaultMinSize(minHeight = 44.dp),
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = OrbitTheme.colors.primary,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}


internal object ButtonConstants {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun defaultButtonColors(
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
