package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
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
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor
import kiwi.orbit.compose.ui.layout.alignByLineHeight
import kiwi.orbit.compose.ui.utils.Strings
import kiwi.orbit.compose.ui.utils.getString

@Composable
public fun Tag(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    active: Boolean = false,
    onSelect: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null,
    removeContentDescription: String = getString(Strings.TagRemove),
    content: @Composable RowScope.() -> Unit,
) {
    val backgroundColor = when {
        selected -> OrbitTheme.colors.info.normal
        active -> OrbitTheme.colors.info.subtle
        else -> OrbitTheme.colors.surface.subtle
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
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .alignByLineHeight()
                        .size(16.dp),
                ) {
                    Icon(
                        Icons.CloseCircle,
                        contentDescription = removeContentDescription,
                        emphasis = ContentEmphasis.Minor,
                    )
                }
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun TagPreview() {
    Preview {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Tag {
                Text("Simple")
            }
            Tag(active = true) {
                Text("Active")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Tag(selected = true) {
                Text("Selected")
            }
            Tag(active = true, selected = true) {
                Text("Active & Selected")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Tag(onRemove = {}) {
                Text("Simple")
            }
            Tag(active = true, onRemove = {}) {
                Text("Active")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Tag(selected = true, onRemove = {}) {
                Text("Selected")
            }
            Tag(active = true, selected = true, onRemove = {}) {
                Text("Active & Selected")
            }
        }
    }
}
