package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalRoundedContainerScope
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.contentColorFor

/**
 * Primary button.
 *
 * ```
 * ButtonPrimary(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        content = content,
    )
}

/**
 * Primary button with subtle color variant.
 *
 * ```
 * ButtonPrimarySubtle(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        content = content,
    )
}

/**
 * Secondary button.
 *
 * ```
 * ButtonSecondary(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        content = content,
    )
}

/**
 * Button for critical action.
 *
 * ```
 * ButtonCritical(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        content = content,
    )
}

/**
 * Button for critical action with subtle color variant.
 *
 * ```
 * ButtonCriticalSubtle(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        content = content,
    )
}

/**
 * Colored button for basic product level visualization.
 *
 * ```
 * ButtonBundleBasic(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        backgroundBrush = OrbitTheme.colors.bundle.basicGradient,
        contentColor = OrbitTheme.colors.bundle.onBasic,
        content = content,
    )
}

/**
 * Colored button for medium product level visualization.
 *
 * ```
 * ButtonBundleMedium(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        backgroundBrush = OrbitTheme.colors.bundle.mediumGradient,
        contentColor = OrbitTheme.colors.bundle.onMedium,
        content = content,
    )
}

/**
 * Colored button for top product level visualization.
 *
 * ```
 * ButtonBundleTop(onClick = {}) {
 *     Text("Click me")
 * }
 * ```
 */
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
        interactionSource = interactionSource,
        modifier = modifier,
        backgroundBrush = OrbitTheme.colors.bundle.topGradient,
        contentColor = OrbitTheme.colors.bundle.onTop,
        content = content,
    )
}

@Composable
internal fun ButtonLargePrimitive(
    onClick: () -> Unit,
    backgroundColor: Color,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush? = null,
    contentArrangement: Arrangement.Horizontal = ButtonDefaults.HorizontalArrangement,
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
    val shape = when (LocalRoundedContainerScope.current) {
        true -> InRoundedContainerCornerShape
        false -> OrbitTheme.shapes.normal
    }
    ButtonPrimitive(
        onClick = onClick,
        backgroundColor = backgroundColor,
        modifier = modifier,
        backgroundBrush = backgroundBrush,
        textStyle = textStyle,
        contentColor = contentColor,
        contentArrangement = contentArrangement,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        shape = shape,
        content = content,
    )
}

private val InRoundedContainerCornerShape = RoundedCornerShape(3.dp)

@OrbitPreviews
@Composable
internal fun ButtonPreview() {
    Preview {
        val mW = Modifier.fillMaxWidth()
        ButtonPrimary({}, mW) { Text("Action") }
        ButtonPrimarySubtle({}, mW) {
            Icon(Icons.Android, contentDescription = null)
            Text("Action")
        }
        ButtonSecondary({}, mW) { Text("Action") }
        ButtonCritical({}, mW) { Text("Action") }
        ButtonCriticalSubtle({}, mW) { Text("Action") }
        ButtonBundleBasic({}, mW) { Text("Action") }
        ButtonBundleMedium({}, mW) { Text("Action") }
        ButtonBundleTop({}, mW) { Text("Action") }
    }
}
