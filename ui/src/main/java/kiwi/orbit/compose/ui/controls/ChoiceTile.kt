package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
public fun ChoiceTile(
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (RowScope.() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    largeHeading: Boolean = true,
    showRadio: Boolean = true,
    content: @Composable (() -> Unit)? = null,
) {
    val errorMessage = stringResource(R.string.orbit_field_default_error)
    val color by animateColorAsState(
        targetValue = when (selected) {
            true -> OrbitTheme.colors.info.normal
            false -> Color.Transparent
        }
    )
    Card(
        onClick = onSelect,
        border = BorderStroke(2.dp, color),
        modifier = modifier
            .semantics {
                this.selected = selected
                if (isError) this.error(errorMessage)
            },
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
                isError = isError,
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
    isError: Boolean,
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
                        true -> OrbitTheme.colors.info.normal
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
                isError = isError,
                onClick = null,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(2.dp)
            )
        }
    }
}

@OrbitPreviews
@Composable
internal fun ChoiceTilePreview() {
    Preview {
        ChoiceTile(
            title = {
                Text(
                    "Multiline long choice title label with many characters",
                    modifier = Modifier.weight(1f)
                )
                BadgeInfoSubtle {
                    Text("Popular")
                }
            },
            description = { Text("Multiline and very long description and lot of words and many characters") },
            icon = { Icon(painter = Icons.BaggageSet, contentDescription = null) },
            selected = false,
            onSelect = {},
            showRadio = true,
            footer = {
                Row(
                    modifier = Modifier.heightIn(min = 32.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        "$16.90",
                        Modifier.weight(1f)
                    )
                }
            }
        ) {
            CustomPlaceholder()
        }
    }
}
