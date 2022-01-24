package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kiwi.orbit.compose.ui.OrbitTheme
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
    badgeContent: @Composable (RowScope.() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    val color by animateColorAsState(
        targetValue = when (selected) {
            true -> OrbitTheme.colors.interactive.main
            false -> Color.Transparent
        }
    )
    Box {
        if (badgeContent != null) {
            BadgeInfo(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
                    .offset(y = BadgeHeight / -2),
                content = badgeContent,
            )
        }
        Card(
            onClick = onSelect,
            modifier = modifier,
            border = BorderStroke(2.dp, color)
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
                )
                ChoiceTileFooter(
                    selected = selected,
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
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon?.invoke()

        ProvideMergedTextStyle(OrbitTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)) {
            title()
        }

        if (description != null) {
            ProvideContentEmphasis(ContentEmphasis.Minor) {
                ProvideMergedTextStyle(
                    value = OrbitTheme.typography.bodyNormal.copy(
                        textAlign = TextAlign.Center,
                    )
                ) {
                    description()
                }
            }
        }

        ProvideMergedTextStyle(
            value = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = when (selected) {
                    true -> OrbitTheme.colors.interactive.main
                    false -> Color.Unspecified
                }
            ),
        ) {
            price()
        }
    }
}

@Composable
private fun ChoiceTileFooter(
    selected: Boolean,
) {
    Radio(
        selected = selected,
        onClick = null,
        modifier = Modifier
            .padding(top = 12.dp)
            .padding(2.dp)
    )
}
