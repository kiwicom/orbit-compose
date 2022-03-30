package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun Tag(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onSelect: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val backgroundColor = when {
        selected -> OrbitTheme.colors.interactive.main
        onRemove != null -> OrbitTheme.colors.interactive.subtle
        else -> OrbitTheme.colors.surface.strong
    }
    val contentColor = contentColorFor(backgroundColor)
    val shape = OrbitTheme.shapes.normal

    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyNormalMedium,
        LocalContentColor provides contentColor,
        LocalContentEmphasis provides ContentEmphasis.Normal,
    ) {
        Row(
            modifier
                .requiredHeight(32.dp)
                .clip(shape)
                .background(backgroundColor, shape)
                .then(if (onSelect != null) Modifier.clickable(onClick = onSelect) else Modifier)
                .semantics {
                    this.selected = selected
                }
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()

            if (onRemove != null) {
                val iconColor = when (selected) {
                    true -> OrbitTheme.colors.interactive.onMain
                    false -> OrbitTheme.colors.interactive.strong
                }
                IconButton(
                    onClick = onRemove,
                    Modifier
                        .padding(start = 8.dp)
                        .size(16.dp),
                ) {
                    Icon(Icons.CloseCircle, tint = iconColor, contentDescription = "Remove") // TODO: l10n
                }
            }
        }
    }
}
