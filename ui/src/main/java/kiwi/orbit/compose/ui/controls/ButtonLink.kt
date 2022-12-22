package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.primary.normal,
        modifier = modifier,
        interactionSource = interactionSource,
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.primary.normal,
        interactionSource = interactionSource,
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.content.normal,
        modifier = modifier,
        interactionSource = interactionSource,
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.content.normal,
        interactionSource = interactionSource,
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.critical.normal,
        modifier = modifier,
        interactionSource = interactionSource,
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        color = OrbitTheme.colors.critical.normal,
        interactionSource = interactionSource,
    )
}

@Composable
private fun ButtonTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    color: Color,
    interactionSource: MutableInteractionSource,
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
                    vertical = ButtonDefaults.ButtonSmallVerticalPadding,
                )
                .clip(OrbitTheme.shapes.normal)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    onClick = onClick,
                    role = Role.Button,
                )
                .padding(
                    horizontal = ButtonDefaults.ButtonSmallHorizontalPadding,
                    vertical = ButtonDefaults.ButtonSmallVerticalPadding,
                ),
        )
    }
}

@OrbitPreviews
@Composable
internal fun ButtonLinkPreview() {
    Preview {
        val mW = Modifier.fillMaxWidth()
        ButtonLinkPrimary({}, mW) { Text("Action") }
        ButtonLinkSecondary({}, mW) { Text("Action") }
        ButtonLinkCritical({}, mW) { Text("Action") }
    }
}

@OrbitPreviews
@Composable
internal fun ButtonTextLinkPreview() {
    Preview {
        Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Text", style = OrbitTheme.typography.title3, modifier = Modifier.alignByBaseline())
            ButtonTextLinkPrimary("Action", {}, modifier = Modifier.alignByBaseline())
            ButtonTextLinkSecondary("Action", {}, modifier = Modifier.alignByBaseline())
            ButtonTextLinkCritical("Action", {}, modifier = Modifier.alignByBaseline())
        }
    }
}
