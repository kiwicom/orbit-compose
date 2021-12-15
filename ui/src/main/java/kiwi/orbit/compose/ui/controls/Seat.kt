package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

@Composable
public fun SeatStandard(
    selected: Boolean,
    label: @Composable () -> Unit,
    price: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SeatContainer(
        selected = selected,
        enabled = true,
        isExtraLegroom = false,
        label = label,
        price = price,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
public fun SeatExtraLegroom(
    selected: Boolean,
    label: @Composable () -> Unit,
    price: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SeatContainer(
        selected = selected,
        enabled = true,
        isExtraLegroom = true,
        label = label,
        price = price,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
public fun SeatUnavailable(
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    SeatContainer(
        selected = false,
        enabled = false,
        isExtraLegroom = true,
        label = {
            Icon(
                painter = Icons.Close,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp),
            )
        },
        price = { Text(stringResource(R.string.orbit_seat_price_unavailable)) },
        onClick = null,
        modifier = modifier,
    )
}

@Composable
private fun SeatContainer(
    selected: Boolean,
    enabled: Boolean,
    isExtraLegroom: Boolean,
    label: @Composable () -> Unit,
    price: @Composable () -> Unit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .width(Width)
                .height(Height)
                .padding(top = PaddingTop)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = onClick != null,
                    indication = null,
                ) { onClick?.invoke() },
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = LayersMargin,
                    start = LayersMargin,
                    end = LayersMargin,
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Seat(
                selected = selected,
                enabled = enabled,
                isExtraLegroom = isExtraLegroom
            ) {
                label()
            }
            SeatPrice {
                price()
            }
        }
        CrossIcon(
            isExtraLegroom = isExtraLegroom,
            selected = selected,
        )
    }
}

@Composable
private fun ColumnScope.Seat(
    selected: Boolean,
    enabled: Boolean,
    isExtraLegroom: Boolean,
    content: @Composable () -> Unit,
) {
    val borderColor by animateColorAsState(
        when {
            !enabled -> ColorTokens.CloudLightHover
            selected -> Color.Transparent
            isExtraLegroom -> ColorTokens.BlueLightActive
            else -> ColorTokens.ProductLightActive
        }
    )

    SeatSurface(
        border = BorderStroke(
            width = BorderWidth,
            color = borderColor,
        ),
    ) {
        val background by animateColorAsState(
            when {
                !enabled -> OrbitTheme.colors.surface.background
                selected && isExtraLegroom -> OrbitTheme.colors.interactive.main
                isExtraLegroom -> OrbitTheme.colors.interactive.subtle
                selected -> OrbitTheme.colors.primary.main
                else -> OrbitTheme.colors.primary.subtle
            }
        )
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .background(background),
            contentAlignment = Alignment.Center,
        ) {
            val color = when {
                selected -> OrbitTheme.colors.interactive.onMain
                isExtraLegroom -> OrbitTheme.colors.interactive.strong.copy(alpha = 0.5f)
                else -> ColorTokens.ProductDark.copy(alpha = 0.4f)
            }
            CompositionLocalProvider(
                LocalContentEmphasis provides when {
                    !enabled -> ContentEmphasis.Disabled
                    else -> ContentEmphasis.Normal
                },
                LocalTextStyle provides OrbitTheme.typography.bodyNormal.plus(
                    TextStyle(
                        color = color,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                )
            ) {
                content()
            }
        }
    }
}

@Composable
private fun ColumnScope.SeatSurface(
    border: BorderStroke,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .drawWithContent {
                drawContent()
                val verticalOffset = Height.value + LayersMargin.toPx() - BorderWidth.toPx()
                drawLine(
                    brush = border.brush,
                    start = Offset(BorderWidth.toPx(), verticalOffset),
                    end = Offset(size.width - BorderWidth.toPx(), verticalOffset),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        shape = RoundedCornerShape(
            topStart = TopRadius,
            topEnd = TopRadius,
            bottomStart = BottomRadius,
            bottomEnd = BottomRadius,
        ),
        border = border,
        content = content,
    )
}

@Composable
private fun SeatPrice(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyNormal.copy(
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
        ),
        LocalContentEmphasis provides ContentEmphasis.Minor,
    ) {
        Box(
            modifier = Modifier.height(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
private fun BoxScope.CrossIcon(
    isExtraLegroom: Boolean,
    selected: Boolean,
) {
    AnimatedVisibility(
        visible = selected,
        modifier = Modifier.align(Alignment.TopEnd),
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Icon(
            painter = Icons.CloseCircle,
            contentDescription = null,
            modifier = Modifier
                .padding(0.5.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(OrbitTheme.colors.surface.main)
                .padding(1.dp),
            tint = when {
                isExtraLegroom -> OrbitTheme.colors.interactive.strong
                else -> OrbitTheme.colors.primary.strong
            },
        )
    }
}

private val BottomRadius = 4.dp
private val BorderWidth = 2.dp
private val Height = 73.dp
private val LayersMargin = 11.dp
private val PaddingTop = 4.dp
private val TopRadius = 10.dp
private val Width = 54.dp
