package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Elevated tile that represents an actionable item.
 *
 * The [title] is required but you can optionally also provide [icon] and [description].
 *
 * The [trailingContent] displays the [Icons.ChevronForward] by default but you can override. [RowScope] is
 * provided in case you want to display more end-aligned elements, e.g. badges. You can also vertically align
 * each element using the [RowScope.align] modifier.
 */
@Composable
public fun Tile(
    title: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    description: @Composable () -> Unit = {},
    trailingContent: @Composable RowScope.() -> Unit = { Icon(Icons.ChevronForward, null) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = OrbitTheme.colors.surface.main,
) {
    TileGroup {
        Tile(
            title = title,
            onClick = onClick,
            modifier = modifier,
            icon = icon,
            description = description,
            trailingContent = trailingContent,
            interactionSource = interactionSource,
            backgroundColor = backgroundColor,
        )
    }
}

/**
 * Elevated tile that represents an actionable item.
 *
 * This variant of the component allows you to define a custom [content].
 *
 * The [trailingContent] displays the [Icons.ChevronForward] by default but you can override. [RowScope] is
 * provided in case you want to display more end-aligned elements, e.g. badges. You can also vertically align
 * each element using the [RowScope.align] modifier.
 */
@Composable
public fun Tile(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = { Icon(Icons.ChevronForward, null) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    content: @Composable () -> Unit,
) {
    TileGroup {
        Tile(
            onClick = onClick,
            modifier = modifier,
            trailingContent = trailingContent,
            interactionSource = interactionSource,
            content = content,
            backgroundColor = backgroundColor,
        )
    }
}

@OrbitPreviews
@Composable
internal fun TilePreview() {
    Preview {
        Tile(
            title = { Text("Title") },
            onClick = {},
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            icon = { Icon(Icons.Airplane, contentDescription = null) },
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            description = { Text("Description") },
            backgroundColor = OrbitTheme.colors.info.subtle,
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            icon = { Icon(Icons.Airplane, contentDescription = null) },
            description = { Text("Description") },
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            description = { Text("Description") },
            trailingContent = {
                Text(
                    text = "Action",
                    modifier = Modifier.align(Alignment.Top),
                    color = OrbitTheme.colors.primary.normal,
                    style = OrbitTheme.typography.bodyNormalMedium,
                )
            },
        )
        Tile(
            onClick = {},
        ) {
            CustomPlaceholder()
        }
    }
}
