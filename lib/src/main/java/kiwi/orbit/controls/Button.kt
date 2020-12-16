package kiwi.orbit.controls

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
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
fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            disabledContentColor = OrbitTheme.colors.surfaceFgSecondary
                .copy(alpha = ContentAlpha.disabled)
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = OrbitTheme.colors.primary.copy(0.12f)
                .compositeOver(OrbitTheme.colors.surface),
            contentColor = OrbitTheme.colors.primary,
        ),
        elevation = MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = OrbitTheme.colors.surfaceSecondary,
        ),
        elevation = MaterialButtonDefaults.elevation(
            defaultElevation = 1.dp
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ButtonLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MaterialButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MaterialTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = OrbitTheme.colors.primary,
            disabledBackgroundColor = Color.Transparent,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}


object ButtonConstants {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun defaultButtonColors(
        backgroundColor: Color = OrbitTheme.colors.primary,
        // We utilize here the secondary color since it has more "color hue" than the primary.
        disabledBackgroundColor: Color = OrbitTheme.colors.surfaceFgSecondary.copy(alpha = 0.12f)
            .compositeOver(OrbitTheme.colors.surface),
        contentColor: Color = contentColorFor(backgroundColor),
        disabledContentColor: Color = contentColor.copy(alpha = ContentAlpha.disabled)
    ): ButtonColors = MaterialButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
    )
}
