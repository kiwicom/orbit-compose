package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun BadgePrimitive(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Unspecified,
    backgroundBrush: Brush? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    icon: (@Composable () -> Unit) = {},
    content: @Composable RowScope.() -> Unit,
) {
    val shape = RoundedCornerShape(50)

    @Suppress("NAME_SHADOWING")
    Row(
        modifier = modifier
            .border(BadgeStrokeWidth, borderColor, shape)
            .then(
                when {
                    backgroundBrush != null -> Modifier.background(backgroundBrush, shape)
                    else -> Modifier.background(backgroundColor, shape)
                }
            )
            .padding(BadgeContentPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val textStyle = LocalTextStyle.current
            .merge(OrbitTheme.typography.bodySmallMedium.copy(textAlign = TextAlign.Center))

        CompositionLocalProvider(
            LocalContentEmphasis provides ContentEmphasis.Normal,
            LocalContentColor provides contentColor,
            LocalTextStyle provides textStyle,
        ) {
            icon.invoke()
            content()
        }
    }
}

internal val BadgeContentPadding = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

internal val BadgeStrokeWidth = 0.5.dp
