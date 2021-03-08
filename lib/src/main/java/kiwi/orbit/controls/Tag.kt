package kiwi.orbit.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.contentColorFor
import kiwi.orbit.icons.Icons

@Composable
public fun Tag(
    selected: Boolean = false,
    onSelect: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val backgroundColor = when {
        selected -> OrbitTheme.colors.interactive
        onRemove != null -> OrbitTheme.colors.interactiveSubtle
        else -> OrbitTheme.colors.surfaceAlt
    }
    val contentColor = contentColorFor(backgroundColor)
    val shape = OrbitTheme.shapes.normal

    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyNormal.copy(fontWeight = FontWeight.Medium),
        LocalContentColor provides contentColor,
        LocalContentAlpha provides 1f,
    ) {
        Row(
            Modifier
                .requiredHeight(32.dp)
                .clip(shape)
                .background(backgroundColor, shape)
                .then(if (onSelect != null) Modifier.clickable(onClick = onSelect) else Modifier)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()

            if (onRemove != null) {
                val iconColor = when (selected) {
                    true -> OrbitTheme.colors.interactiveContent
                    false -> OrbitTheme.colors.interactiveStrong
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
