package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
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
 * Elevated tile group that represents a group of actionable items.
 * Specifically designed to hold a group of [TileGroupScope.Tile]s.
 *
 * The [separatorStartIndent] is optional override of the start indent of separators
 * between [TileGroupScope.Tile]s.
 */
@Composable
public fun TileGroup(
    modifier: Modifier = Modifier,
    separatorStartIndent: Dp = 16.dp,
    content: @Composable TileGroupScope.() -> Unit,
) {
    val tileGroupScope = remember(separatorStartIndent) { TileGroupScopeInstance(separatorStartIndent) }
    SurfaceCard(
        modifier = modifier,
    ) {
        TileGroupColumn {
            tileGroupScope.content()
        }
    }
}

@LayoutScopeMarker
@Immutable
public interface TileGroupScope {
    public val separatorStartIndent: Dp
}

private class TileGroupScopeInstance(
    override val separatorStartIndent: Dp,
) : TileGroupScope

/**
 * A tile placeable within elevated TileGroup that represents an actionable item.
 *
 * The [title] is required but you can optionally also provide [icon] and [description].
 *
 * The [trailingContent] displays the [Icons.ChevronForward] by default but you can override. [RowScope] is
 * provided in case you want to display more end-aligned elements, e.g. badges. You can also vertically align
 * each element using the [RowScope.align] modifier.
 */
@Composable
public fun TileGroupScope.Tile(
    title: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    description: @Composable () -> Unit = {},
    trailingContent: @Composable RowScope.() -> Unit = { Icon(Icons.ChevronForward, null) },
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Tile(
        onClick = onClick,
        modifier = modifier,
        trailingContent = trailingContent,
        backgroundColor = backgroundColor,
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
 * A tile placeable within elevated TileGroup that represents an actionable item.
 *
 * This variant of the component allows you to define a custom [content].
 *
 * The [trailingContent] displays the [Icons.ChevronForward] by default but you can override. [RowScope] is
 * provided in case you want to display more end-aligned elements, e.g. badges. You can also vertically align
 * each element using the [RowScope.align] modifier.
 */
@Composable
public fun TileGroupScope.Tile(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = { Icon(Icons.ChevronForward, null) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        color = backgroundColor,
        interactionSource = interactionSource,
    ) {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {
                Box {
                    content()
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                        ProvideContentEmphasis(ContentEmphasis.Minor) {
                            trailingContent()
                        }
                    }
                }
            },
        ) { measurables, constraints ->
            val trailingWidth = measurables[1].maxIntrinsicWidth(Int.MAX_VALUE)
            val occupied = trailingWidth.takeIf { it != 0 }?.plus(12.dp.roundToPx()) ?: 0
            val contentWidth = (constraints.maxWidth - occupied).coerceAtLeast(0)
            val contentPlaceable = measurables[0].measure(
                Constraints.fixedWidth(width = contentWidth),
            )
            val trailingPlaceable = measurables[1].measure(
                Constraints.fixed(
                    width = trailingWidth,
                    height = contentPlaceable.height,
                ),
            )
            layout(constraints.maxWidth, contentPlaceable.height) {
                contentPlaceable.placeRelative(0, 0)
                trailingPlaceable.placeRelative(
                    x = constraints.maxWidth - trailingPlaceable.width,
                    y = 0,
                )
            }
        }
    }
    Separator(startIndent = separatorStartIndent)
}

@Composable
private fun TileGroupColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            content()
        },
    ) { measurables, constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        check(measurables.size.mod(2) == 0) {
            "TileGroup container can render only Tile composables."
        }

        // drop the last measurable, typically the last separator added by [TileGroupScope.Tile].
        val placeables = measurables.dropLast(1).map {
            it.measure(looseConstraints)
        }

        val columnWidth = placeables.maxOfOrNull { it.width } ?: 0
        val columnHeight = placeables.sumOf { it.height }

        layout(columnWidth, columnHeight) {
            var currentY = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(0, currentY)
                currentY += placeable.height
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun TileGroupPreview() {
    Preview {
        TileGroup {
            Tile(
                title = { Text("Title") },
                onClick = { },
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
}
