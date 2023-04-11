package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun ButtonPrimitive(
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OrbitTheme.shapes.normal,
    textStyle: TextStyle = OrbitTheme.typography.bodyNormalMedium.copy(textAlign = TextAlign.Center),
    contentArrangement: Arrangement.Horizontal = ButtonDefaults.HorizontalArrangement,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val textStyle = LocalTextStyle.current.merge(textStyle)

    CompositionLocalProvider(
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalContentColor provides contentColor,
        LocalTextStyle provides textStyle,
    ) {
        Box(
            modifier
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .then(
                    when {
                        backgroundBrush != null -> Modifier.background(backgroundBrush)
                        else -> Modifier.background(backgroundColor)
                    },
                ),
            propagateMinConstraints = true,
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = if (LocalSmallButtonScope.current) ButtonDefaults.MinHeight else ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight,
                    )
                    .padding(contentPadding),
                horizontalArrangement = contentArrangement,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

public object ButtonDefaults {
    internal val ButtonHorizontalPadding = 16.dp
    internal val ButtonVerticalPadding = 12.dp
    internal val ButtonSmallHorizontalPadding = 8.dp
    internal val ButtonSmallVerticalPadding = 8.dp

    public val ContentPadding: PaddingValues = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding,
    )

    public val SmallContentPadding: PaddingValues = PaddingValues(
        start = ButtonSmallHorizontalPadding,
        top = ButtonSmallVerticalPadding,
        end = ButtonSmallHorizontalPadding,
        bottom = ButtonSmallVerticalPadding,
    )

    public val HorizontalArrangement: Arrangement.Horizontal =
        Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)

    /**
     * The default min width applied for the [ButtonPrimitive].
     * You can override it by applying Modifier.widthIn directly on [ButtonPrimitive].
     */
    public val MinWidth: Dp = 64.dp

    /**
     * The default min width applied for the [ButtonPrimitive].
     * You can override it by applying Modifier.heightIn directly on [ButtonPrimitive].
     */
    public val MinHeight: Dp = 32.dp
}
