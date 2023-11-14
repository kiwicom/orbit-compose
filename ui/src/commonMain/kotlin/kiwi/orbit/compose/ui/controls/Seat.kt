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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.utils.Strings
import kiwi.orbit.compose.ui.utils.getString

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
        price = { Text(getString(Strings.SeatPriceUnavailable)) },
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
        modifier = modifier
            .width(SeatContainerWidth)
            .height(SeatContainerHeight)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                enabled = onClick != null,
                indication = null,
                role = Role.Button,
                onClick = { onClick?.invoke() },
            )
            .semantics {
                this.selected = selected
            },
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = SeatContainerLayersMargin,
                    start = SeatContainerLayersMargin,
                    end = SeatContainerLayersMargin,
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Seat(
                selected = selected,
                enabled = enabled,
                isExtraLegroom = isExtraLegroom,
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
        targetValue = resolveBorderColor(enabled, selected, isExtraLegroom),
        label = "SeatBorderColor",
    )

    SeatSurface(
        border = BorderStroke(
            width = SeatBorderWidth,
            color = borderColor,
        ),
    ) {
        val background by animateColorAsState(
            when {
                !enabled -> OrbitTheme.colors.surface.subtle
                selected && isExtraLegroom -> OrbitTheme.colors.info.normal
                isExtraLegroom -> OrbitTheme.colors.info.subtle
                selected -> OrbitTheme.colors.primary.normal
                else -> OrbitTheme.colors.primary.subtle
            },
            label = "SeatBackgroundColor",
        )
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .background(background),
            contentAlignment = Alignment.Center,
        ) {
            val color = when {
                selected -> OrbitTheme.colors.info.onNormal
                isExtraLegroom -> OrbitTheme.colors.info.strong
                else -> OrbitTheme.colors.primary.strong
            }
            CompositionLocalProvider(
                LocalContentEmphasis provides when {
                    !enabled -> ContentEmphasis.Disabled
                    else -> ContentEmphasis.Normal
                },
                LocalTextStyle provides OrbitTheme.typography.bodyNormal.copy(
                    color = color,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                ),
            ) {
                content()
            }
        }
    }
}

@Composable
private fun resolveBorderColor(
    enabled: Boolean,
    selected: Boolean,
    isExtraLegroom: Boolean,
): Color = when {
    !enabled -> OrbitTheme.colors.surface.subtleAlt
    selected -> Color.Transparent
    isExtraLegroom -> {
        OrbitTheme.colors.info.normal
            .copy(alpha = 0.2f) // custom, missing theme color
            .compositeOver(OrbitTheme.colors.surface.main)
    }
    else -> {
        OrbitTheme.colors.primary.normal
            .copy(alpha = 0.2f) // custom, missing theme color
            .compositeOver(OrbitTheme.colors.surface.main)
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
                val verticalOffset = SeatBorderExtraBottomOffset.toPx()
                drawLine(
                    brush = border.brush,
                    start = Offset(SeatBorderWidth.toPx(), verticalOffset),
                    end = Offset(size.width - SeatBorderWidth.toPx(), verticalOffset),
                    strokeWidth = SeatBorderWidth.toPx(),
                )
            },
        shape = RoundedCornerShape(
            topStart = SeatTopRadius,
            topEnd = SeatTopRadius,
            bottomStart = SeatBottomRadius,
            bottomEnd = SeatBottomRadius,
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
            modifier = Modifier.height(SeatPriceHeight),
            contentAlignment = BiasAlignment(
                horizontalBias = 0f,
                verticalBias = 0.6f,
            ),
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
                .size(22.dp)
                .padding(0.66.dp)
                .clip(CircleShape)
                .background(OrbitTheme.colors.surface.main)
                .padding(0.5.dp),
            tint = when {
                isExtraLegroom -> OrbitTheme.colors.info.strong
                else -> OrbitTheme.colors.primary.strong
            },
        )
    }
}

// Primary Seat constants
private val SeatBorderWidth = 2.dp
private val SeatBottomRadius = 4.dp
private val SeatHeight = 36.dp
private val SeatTopRadius = 10.dp
private val SeatWidth = 32.dp

// Derived Seat constants
private val SeatBorderExtraBottomOffset = SeatHeight - SeatBorderWidth * 1.5f

// Primary SeatPrice constants
private val SeatPriceHeight = 16.dp

// Primary SeatContainer constants
private val SeatContainerLayersMargin = 9.dp

// Derived SeatContainer constants
private val SeatContainerHeight = SeatHeight + SeatPriceHeight + SeatContainerLayersMargin
private val SeatContainerWidth = SeatWidth + SeatContainerLayersMargin * 2

@OrbitPreviews
@Composable
internal fun SeatPreview() {
    Preview {
        Row {
            Column {
                SeatExtraLegroom(
                    selected = false,
                    label = { Text("MO") },
                    price = { Text("€19.99") },
                    onClick = {},
                )

                SeatStandard(
                    selected = false,
                    label = { Text("MO") },
                    price = { Text("€12.99") },
                    onClick = {},
                )

                SeatUnavailable(
                    contentDescription = "Unavailable",
                )
            }
            Column {
                SeatExtraLegroom(
                    selected = true,
                    label = { Text("MO") },
                    price = { Text("€19.99") },
                    onClick = {},
                )

                SeatStandard(
                    selected = true,
                    label = { Text("MO") },
                    price = { Text("€12.99") },
                    onClick = {},
                )
            }
        }
    }
}
