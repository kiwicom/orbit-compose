package kiwi.orbit.compose.ui.controls.field

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis

@Composable
internal fun FieldContent(
    innerContent: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
) {
    Layout(
        content = {
            if (leadingIcon != null) {
                Box(Modifier.layoutId("leading")) {
                    if (onLeadingIconClick != null) {
                        IconButton(
                            onClick = onLeadingIconClick,
                            rippleRadius = RippleRadius,
                        ) {
                            leadingIcon()
                        }
                    } else {
                        leadingIcon()
                    }
                }
            }
            if (trailingIcon != null) {
                Box(Modifier.layoutId("trailing")) {
                    if (onTrailingIconClick != null) {
                        IconButton(
                            onClick = onTrailingIconClick,
                            rippleRadius = RippleRadius,
                        ) {
                            trailingIcon()
                        }
                    } else {
                        trailingIcon()
                    }
                }
            }
            if (placeholder != null) {
                Box(Modifier.layoutId("placeholder")) {
                    ProvideContentEmphasis(ContentEmphasis.Subtle, content = placeholder)
                }
            }
            Box(Modifier.layoutId("textField")) {
                innerContent()
            }
        },
    ) { measurables, incomingConstraints ->
        val constraints = incomingConstraints.copy(minWidth = 0, minHeight = 0)
        val padding = 12.dp.roundToPx()

        val leadingPlaceable = measurables.find { it.layoutId == "leading" }
            ?.measure(constraints)
        val leadingWidth = leadingPlaceable?.width?.plus(padding) ?: 0

        val iconPaddingWidth = (24.dp - RippleRadius) * 2
        val trailingPadding =
            if (onTrailingIconClick != null) padding - iconPaddingWidth.roundToPx() else padding
        val trailingPlaceable = measurables.find { it.layoutId == "trailing" }
            ?.measure(constraints.offset(horizontal = -leadingWidth))
        val trailingWidth = trailingPlaceable?.width?.plus(trailingPadding) ?: 0

        val occupiedHorizontally = padding * 2 + leadingWidth + trailingWidth
        val textFieldConstraints = incomingConstraints
            .copy(minHeight = 0)
            .offset(horizontal = -occupiedHorizontally)

        val placeholderPlaceable =
            measurables.find { it.layoutId == "placeholder" }?.measure(textFieldConstraints)
        val textFieldPlaceable =
            measurables.first { it.layoutId == "textField" }.measure(textFieldConstraints)

        val width = constraints.maxWidth
        val height = textFieldPlaceable.height + padding * 2

        val textVerticalPosition = if (singleLine) {
            Alignment.CenterVertically.align(textFieldPlaceable.height, height)
        } else {
            padding
        }

        layout(width, height) {
            leadingPlaceable?.placeRelative(
                padding,
                Alignment.CenterVertically.align(leadingPlaceable.height, height)
            )
            trailingPlaceable?.placeRelative(
                width - trailingWidth,
                Alignment.CenterVertically.align(trailingPlaceable.height, height)
            )
            placeholderPlaceable?.placeRelative(
                padding + leadingWidth,
                textVerticalPosition,
                zIndex = 1f
            )
            textFieldPlaceable.placeRelative(padding + leadingWidth, textVerticalPosition)
        }
    }
}

private val RippleRadius = 20.dp
