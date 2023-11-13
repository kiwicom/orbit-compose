package kiwi.orbit.compose.ui.controls.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.IconButton

@Composable
internal fun FieldIcon(
    onClick: (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    leading: Boolean,
    modifier: Modifier = Modifier,
) {
    if (icon == null) {
        Spacer(modifier.width(FieldIconPadding))
    } else if (onClick != null) {
        IconButton(
            modifier = modifier.padding(
                start = if (leading) FieldIconButtonPadding else FieldIconButtonSeparatorPadding,
                end = if (leading) FieldIconButtonSeparatorPadding else FieldIconButtonPadding,
            ),
            onClick = onClick,
            rippleRadius = RippleRadius,
            content = icon,
        )
    } else {
        Box(
            modifier = modifier.padding(
                start = if (leading) FieldIconPadding else FieldIconSeparatorPadding,
                end = if (leading) FieldIconSeparatorPadding else FieldIconPadding,
            ),
        ) {
            icon()
        }
    }
}

private val RippleRadius = 20.dp
private val FieldIconPadding = 12.dp
private val FieldIconSeparatorPadding = 8.dp
private val FieldIconButtonPadding = 2.dp // 12.dp - 10.dp ripple padding
private val FieldIconButtonSeparatorPadding = 0.dp // 8.dp - 10.dp ripple padding
