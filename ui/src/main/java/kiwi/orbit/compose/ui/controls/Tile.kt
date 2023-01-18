package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

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
) {
    Tile(
        onClick = onClick,
        modifier = modifier,
        trailingContent = trailingContent,
        interactionSource = interactionSource,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.title4) {
                icon()
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ProvideMergedTextStyle(OrbitTheme.typography.title4) {
                    title()
                }
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                    description()
                }
            }
        }
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
    content: @Composable () -> Unit,
) {
    SurfaceCard(
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(Modifier.weight(1f)) {
                content()
            }
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                    ProvideContentEmphasis(ContentEmphasis.Minor) {
                        trailingContent()
                    }
                }
            }
        }
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
