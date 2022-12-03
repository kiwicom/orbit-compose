package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalSmallButtonScope
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

/**
 * List choice component.
 *
 * Represents a menu item. Optionally provide a [description], [icon] or [trailingIcon].
 * Separator is drawn by default.
 *
 * The [trailingIcon] icon slot is suitable for other end-aligned UI, e.g. badges.
 */
@Composable
public fun ListChoice(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    description: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    withSeparator: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    title: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = rememberRipple(),
                interactionSource = interactionSource,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        content = {
            Box(Modifier.layoutId(IconLayoutId)) {
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                    icon()
                }
            }
            Box(Modifier.layoutId(TitleLayoutId)) {
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                    title()
                }
            }
            Box(Modifier.layoutId(DescriptionLayoutId)) {
                ProvideMergedTextStyle(OrbitTheme.typography.bodySmall) {
                    ProvideContentEmphasis(ContentEmphasis.Minor) {
                        description()
                    }
                }
            }
            Box(Modifier.layoutId(TrailingIconLayoutId)) {
                ProvideContentEmphasis(ContentEmphasis.Minor) {
                    CompositionLocalProvider(
                        LocalSmallButtonScope provides true,
                    ) {
                        trailingIcon()
                    }
                }
            }
            if (withSeparator) {
                Box(Modifier.layoutId(SeparatorLayoutId)) {
                    Separator()
                }
            }
        },
    ) { measurables, constraints ->
        val iconMeasurable = measurables.first { it.layoutId == IconLayoutId }
        val titleMeasurable = measurables.first { it.layoutId == TitleLayoutId }
        val descriptionMeasurable = measurables.first { it.layoutId == DescriptionLayoutId }
        val trailingIconMeasurable = measurables.first { it.layoutId == TrailingIconLayoutId }
        val separatorMeasurable = measurables.find { it.layoutId == SeparatorLayoutId }

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val iconPlaceable = iconMeasurable.measure(looseConstraints)
        val iconWidth = iconPlaceable.width
        val iconIndent = if (iconWidth == 0) 0 else iconWidth + 8.dp.roundToPx()

        val trailingIconPlaceable = trailingIconMeasurable.measure(looseConstraints)
        val trailingIconWidth = trailingIconPlaceable.width
        val trailingIconIndent = if (trailingIconWidth == 0) 0 else trailingIconWidth + 8.dp.roundToPx()

        val contentConstraints = constraints
            .copy(minHeight = 0)
            .offset(horizontal = -(iconIndent + trailingIconIndent))
        val titlePlaceable = titleMeasurable.measure(contentConstraints)
        val descriptionPlaceable = descriptionMeasurable.measure(contentConstraints)

        val width = iconIndent +
            maxOf(titlePlaceable.width, descriptionPlaceable.width) +
            trailingIconIndent

        val separatorPlaceable = separatorMeasurable?.measure(
            constraints.copy(maxWidth = width).offset(horizontal = -iconIndent + 16.dp.roundToPx()),
        )

        val mainHeight = titlePlaceable.height + descriptionPlaceable.height
        val trailingHeight = trailingIconPlaceable.height
        val height = maxOf(mainHeight, trailingHeight) + (separatorPlaceable?.height ?: 0)

        val contentYShift = if (trailingHeight > mainHeight) (trailingHeight - mainHeight) / 2 else 0

        layout(width, height) {
            iconPlaceable.placeRelative(
                x = 0,
                y = contentYShift,
            )
            trailingIconPlaceable.placeRelative(
                x = width - trailingIconPlaceable.width,
                y = (maxOf(mainHeight, trailingHeight) - trailingIconPlaceable.height) / 2,
            )
            titlePlaceable.placeRelative(
                x = iconIndent,
                y = contentYShift,
            )
            descriptionPlaceable.placeRelative(
                x = iconIndent,
                y = contentYShift + titlePlaceable.height,
            )
            separatorPlaceable?.placeRelative(
                x = iconIndent,
                y = height + 11.dp.roundToPx(), // draws outside the Layout boundaries
            )
        }
    }
}

private const val IconLayoutId = "icon"
private const val TitleLayoutId = "content"
private const val DescriptionLayoutId = "description"
private const val TrailingIconLayoutId = "trailing_icon"
private const val SeparatorLayoutId = "separator"

@OrbitPreviews
@Composable
internal fun ListChoicePreview() {
    Preview {
        Column {
            ListChoice(onClick = {}) {
                Text("ListChoice title")
            }
            ListChoice(
                onClick = {},
                description = { Text("Further description") },
            ) {
                Text("ListChoice title")
            }
            ListChoice(
                onClick = {},
                icon = { Icon(Icons.Accommodation, contentDescription = null) },
                trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
            ) {
                Text("ListChoice title")
            }
            ListChoice(
                onClick = {},
                icon = { Icon(Icons.Accommodation, contentDescription = null) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BadgeCircleInfoSubtle(value = 1)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.ChevronForward, contentDescription = null)
                    }
                },
                description = { Text("Further description") },
            ) {
                Text("ListChoice title")
            }
            ListChoice(
                onClick = {},
                icon = { Icon(Icons.Bus, contentDescription = null) },
                trailingIcon = {
                    ButtonPrimarySubtle(onClick = {}) {
                        Icon(Icons.Plus, contentDescription = null)
                    }
                },
            ) {
                Text("ListChoice title")
            }
        }
    }
}
