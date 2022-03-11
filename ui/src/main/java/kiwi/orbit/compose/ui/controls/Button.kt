package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalAlertScope
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.primary.main,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonPrimarySubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.primary.subtle,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonSecondary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.surface.strong,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonCritical(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.critical.main,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonCriticalSubtle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        backgroundColor = OrbitTheme.colors.critical.subtle,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
public fun ButtonLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        backgroundColor = Color.Transparent,
        contentColor = OrbitTheme.colors.primary.main,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
private fun Button(
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = contentColorFor(backgroundColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OrbitTheme.shapes.normal,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val inAlertScope = LocalAlertScope.current

    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = border,
        onClick = onClick,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {
        ProvideContentEmphasis(
            emphasis = ContentEmphasis.Normal
        ) {
            ProvideMergedTextStyle(
                value = OrbitTheme.typography.bodyNormalMedium.copy(
                    textAlign = TextAlign.Center,
                ),
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = when (inAlertScope) {
                                true -> ButtonDefaults.MinAlertHeight
                                false -> ButtonDefaults.MinHeight
                            }
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

public object ButtonDefaults {
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp

    /**
     * The default content padding used by [Button]
     */
    public val ContentPadding: PaddingValues = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding
    )

    /**
     * The default min width applied for the [Button].
     * Note that you can override it by applying Modifier.widthIn directly on [Button].
     */
    public val MinWidth: Dp = 64.dp

    /**
     * The default min width applied for the [Button].
     * Note that you can override it by applying Modifier.heightIn directly on [Button].
     */
    public val MinHeight: Dp = 44.dp

    public val MinAlertHeight: Dp = 32.dp
}
