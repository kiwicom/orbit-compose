package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.layout.expand

/**
 * ButtonLink with common button appearance.
 *
 * Consider using [ButtonTextLinkPrimary] for alignment with Text.
 */
@Composable
public fun ButtonLinkPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.primary.normal,
        modifier = modifier,
        content = content,
    )
}

/**
 * Text aligning version of ButtonLink.
 *
 * Suitable for aligning with other text (both horizontally and vertically).
 * Touch feedback is drawn 8.dp outside the composable boundaries.
 */
@Composable
public fun ButtonTextLinkPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.primary.normal,
    )
}

/**
 * ButtonLink with common button appearance.
 *
 * Consider using [ButtonTextLinkSecondary] for alignment with Text.
 */
@Composable
public fun ButtonLinkSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.content.normal,
        modifier = modifier,
        content = content,
    )
}

/**
 * Text aligning version of ButtonLink.
 *
 * Suitable for aligning with other text (both horizontally and vertically).
 * Touch feedback is drawn 8.dp outside the composable boundaries.
 */
@Composable
public fun ButtonTextLinkSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.content.normal,
    )
}

/**
 * ButtonLink with common button appearance.
 *
 * Consider using [ButtonTextLinkCritical] for alignment with Text.
 */
@Composable
public fun ButtonLinkCritical(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.critical.normal,
        modifier = modifier,
        content = content,
    )
}

/**
 * Text aligning version of ButtonLink.
 *
 * Suitable for aligning with other text (both horizontally and vertically).
 * Touch feedback is drawn 8.dp outside the composable boundaries.
 */
@Composable
public fun ButtonTextLinkCritical(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.critical.normal,
    )
}

@Composable
private fun ButtonTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    color: Color,
) {
    CompositionLocalProvider(
        LocalContentColor provides color,
    ) {
        Text(
            text = text,
            style = OrbitTheme.typography.bodyNormalMedium,
            modifier = modifier
                .expand(
                    horizontal = ButtonDefaults.ButtonSmallHorizontalPadding,
                    vertical = ButtonDefaults.ButtonSmallVerticalPadding
                )
                .clip(OrbitTheme.shapes.normal)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick,
                    role = Role.Button,
                )
                .padding(
                    horizontal = ButtonDefaults.ButtonSmallHorizontalPadding,
                    vertical = ButtonDefaults.ButtonSmallVerticalPadding
                )
        )
    }
}
