package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
public fun ChoiceTileCentered(
    selected: Boolean,
    onSelect: () -> Unit,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    price: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    badgeContent: @Composable (RowScope.() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    largeHeading: Boolean = true,
) {
    val errorMessage = stringResource(R.string.orbit_field_default_error)
    val color by animateColorAsState(
        targetValue = when (selected) {
            true -> OrbitTheme.colors.info.normal
            false -> Color.Transparent
        },
    )
    Box(
        modifier = modifier
            .semantics {
                this.selected = selected
                if (isError) this.error(errorMessage)
            },
        propagateMinConstraints = true,
    ) {
        if (badgeContent != null) {
            Box(Modifier.zIndex(1f)) {
                BadgeInfo(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(0, placeable.height / -2)
                            }
                        },
                    content = badgeContent,
                )
            }
        }
        SurfaceCard(
            onClick = onSelect,
            border = BorderStroke(2.dp, color),
        ) {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 22.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ChoiceTileContent(
                    selected = selected,
                    icon = icon,
                    title = title,
                    description = description,
                    price = price,
                    largeHeading = largeHeading,
                )
                ChoiceTileFooter(
                    selected = selected,
                    isError = isError,
                )
            }
        }
    }
}

@Composable
private fun ChoiceTileContent(
    selected: Boolean,
    icon: @Composable (() -> Unit)?,
    title: @Composable () -> Unit,
    description: @Composable (() -> Unit)?,
    price: @Composable () -> Unit,
    largeHeading: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Provide default icon size to 24.dp
        ProvideMergedTextStyle(OrbitTheme.typography.title3) {
            icon?.invoke()
        }
        val headingStyle = when (largeHeading) {
            true -> OrbitTheme.typography.title3
            false -> OrbitTheme.typography.title4
        }.copy(textAlign = TextAlign.Center)
        ProvideMergedTextStyle(headingStyle) {
            title()
        }

        if (description != null) {
            ProvideContentEmphasis(ContentEmphasis.Minor) {
                ProvideMergedTextStyle(
                    value = OrbitTheme.typography.bodyNormal.copy(
                        textAlign = TextAlign.Center,
                    ),
                ) {
                    description()
                }
            }
        }

        ProvideMergedTextStyle(
            value = OrbitTheme.typography.title3.copy(
                textAlign = TextAlign.Center,
                color = when (selected) {
                    true -> OrbitTheme.colors.info.normal
                    false -> Color.Unspecified
                },
            ),
        ) {
            price()
        }
    }
}

@Composable
private fun ChoiceTileFooter(
    selected: Boolean,
    isError: Boolean,
) {
    Radio(
        selected = selected,
        onClick = null,
        isError = isError,
        modifier = Modifier
            .padding(top = 12.dp)
            .padding(2.dp),
    )
}

@OrbitPreviews
@Composable
internal fun ChoiceTileCenteredPreview() {
    Preview {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ChoiceTileCentered(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                selected = false,
                onSelect = {},
                icon = { Icon(painter = Icons.BaggageSet, contentDescription = null) },
                title = { Text("Plus Support") },
                description = { Text("Everyone sits together") },
                price = { Text("+ 10 €") },
            )
            ChoiceTileCentered(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                selected = true,
                onSelect = {},
                title = { Text("Minus Support") },
                description = {},
                price = { Text("+ 10 €") },
            )
        }
    }
}
