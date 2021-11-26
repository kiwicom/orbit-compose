package kiwi.orbit.compose.ui.controls.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.IconButton

@Composable
internal fun FieldIcon(
    layoutId: String,
    onClick: (() -> Unit)?,
    icon: @Composable () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier
            .layoutId(layoutId)
            .sizeIn(maxWidth = 20.dp, maxHeight = 20.dp)
    ) {
        if (onClick != null) {
            IconButton(
                onClick = onClick,
                rippleRadius = RippleRadius,
                content = icon
            )
        } else {
            icon()
        }
    }
}

private val RippleRadius = 20.dp
