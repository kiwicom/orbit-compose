package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.primary.normal,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.primary.subtle,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.surface.normal,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonCritical(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.critical.normal,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonCriticalSubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.critical.subtle,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonBundleBasic(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.basicGradient,
        contentColor = OrbitTheme.colors.bundle.onBasic,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonBundleMedium(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.mediumGradient,
        contentColor = OrbitTheme.colors.bundle.onMedium,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
public fun ButtonBundleTop(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonLargePrimitive(
        onClick = onClick,
        backgroundColor = Color.Unspecified,
        backgroundBrush = OrbitTheme.colors.bundle.topGradient,
        contentColor = OrbitTheme.colors.bundle.onTop,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
internal fun ButtonLargePrimitive(
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush? = null,
    interactionSource: MutableInteractionSource,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable RowScope.() -> Unit,
) {
    val isSmall = LocalSmallButtonScope.current
    val contentPadding = when (isSmall) {
        true -> ButtonDefaults.SmallContentPadding
        false -> ButtonDefaults.ContentPadding
    }
    val textStyle = when (isSmall) {
        true -> OrbitTheme.typography.bodySmallMedium.copy(textAlign = TextAlign.Center)
        false -> OrbitTheme.typography.bodyNormalMedium.copy(textAlign = TextAlign.Center)
    }
    ButtonPrimitive(
        onClick = onClick,
        backgroundColor = backgroundColor,
        modifier = modifier,
        backgroundBrush = backgroundBrush,
        textStyle = textStyle,
        contentColor = contentColor,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@OrbitPreviews
@Composable
internal fun ButtonPreview() {
    Preview {
        val mW = Modifier.fillMaxWidth()
        ButtonPrimary({}, mW) { Text("Action") }
        ButtonPrimarySubtle({}, mW) { Text("Action") }
        ButtonSecondary({}, mW) { Text("Action") }
        ButtonCritical({}, mW) { Text("Action") }
        ButtonCriticalSubtle({}, mW) { Text("Action") }
        ButtonBundleBasic({}, mW) { Text("Action") }
        ButtonBundleMedium({}, mW) { Text("Action") }
        ButtonBundleTop({}, mW) { Text("Action") }
    }
}
