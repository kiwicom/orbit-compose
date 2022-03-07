package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
public fun ChoiceTile(
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (RowScope.() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    largeHeading: Boolean = true,
    showRadio: Boolean = true,
    content: @Composable (() -> Unit)? = null,
) {
    val color by animateColorAsState(
        targetValue = when (selected) {
            true -> OrbitTheme.colors.interactive.main
            false -> Color.Transparent
        }
    )
    Card(
        onClick = onSelect,
        modifier = modifier,
        border = BorderStroke(2.dp, color)
    ) {
        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            ChoiceTileContent(
                icon = icon,
                title = title,
                description = description,
                largeHeading = largeHeading,
                content = content,
            )
            ChoiceTileFooter(
                footer = footer,
                selected = selected,
                showRadio = showRadio,
            )
        }
    }
}

@Composable
private fun ChoiceTileContent(
    icon: @Composable (() -> Unit)?,
    title: @Composable (RowScope.() -> Unit)?,
    description: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)?,
    largeHeading: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (title != null || description != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val headingStyle = when (largeHeading) {
                    true -> OrbitTheme.typography.title3
                    false -> OrbitTheme.typography.title4
                }
                ProvideMergedTextStyle(headingStyle) {
                    icon?.invoke()
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    if (title != null) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ProvideMergedTextStyle(headingStyle) {
                                title()
                            }
                        }
                    }
                    if (description != null) {
                        ProvideContentEmphasis(ContentEmphasis.Minor) {
                            ProvideMergedTextStyle(value = OrbitTheme.typography.bodyNormal) {
                                description()
                            }
                        }
                    }
                }
            }
        }

        content?.invoke()
    }
}

@Composable
private fun ChoiceTileFooter(
    footer: @Composable (() -> Unit)?,
    selected: Boolean,
    showRadio: Boolean,
) {
    Row(
        Modifier
            .padding(top = 12.dp)
    ) {
        Box(
            Modifier.weight(1f),
        ) {
            ProvideMergedTextStyle(
                value = OrbitTheme.typography.title3.copy(
                    color = when (selected) {
                        true -> OrbitTheme.colors.interactive.main
                        false -> Color.Unspecified
                    }
                ),
            ) {
                if (footer != null) {
                    footer()
                }
            }
        }

        if (showRadio) {
            Radio(
                selected = selected,
                onClick = null,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(2.dp)
            )
        }
    }
}
