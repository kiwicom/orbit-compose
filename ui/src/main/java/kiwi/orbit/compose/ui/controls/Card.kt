package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

/**
 * Rectangular surface that separates content into sections.
 * For rounded and elevated card see [SurfaceCard].
 *
 * The [content] is by default surrounded with appropriate padding.
 *
 * You can override the default padding with the [contentPadding] parameter. This allows the [Card] to be
 * combined with other components that handle padding on their own, e.g. [ListChoice].
 *
 * Optionally, you can supply [title] and [action] that together make up a header for the [Card].
 * Appropriate text styles are provided. These slots are vertically aligned by their baselines.
 */
@Composable
public fun Card(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit,
) {
    Surface(modifier) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        // Header has 16dp top padding but only if it has some content in it.
                        val topPadding = if (placeable.height > 0) 16.dp.roundToPx() else 0

                        val width = placeable.width
                        val height = (placeable.height + topPadding).coerceAtMost(constraints.maxHeight)

                        layout(width, height) {
                            placeable.placeRelative(0, topPadding)
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .alignByBaseline()
                        .weight(1f),
                ) {
                    ProvideMergedTextStyle(OrbitTheme.typography.title4) {
                        title()
                    }
                }
                Box(Modifier.alignByBaseline()) {
                    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                        action()
                    }
                }
            }
            Box(Modifier.padding(contentPadding)) {
                content()
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun CardPreview() {
    Preview(Modifier.background(OrbitTheme.colors.surface.normal)) {
        Card {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        ) {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title", style = OrbitTheme.typography.title1) },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        ) {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title") },
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Column {
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneTakeoff, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Takeoff")
                }
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneLanding, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Landing")
                }
            }
        }
    }
}
