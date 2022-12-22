package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.field.FieldContent
import kiwi.orbit.compose.ui.controls.field.FieldLabel
import kiwi.orbit.compose.ui.controls.field.FieldMessage
import kiwi.orbit.compose.ui.controls.internal.ColumnWithMinConstraints
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

@Composable
public fun ClickableField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ColumnWithMinConstraints(modifier) {
        if (label != null) {
            FieldLabel(label)
        }

        ClickableFieldBox(
            value = value,
            isError = error != null,
            onClick = onClick,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            interactionSource = interactionSource,
        )

        FieldMessage(error, info)
    }
}

@Composable
internal fun ClickableFieldBox(
    value: String,
    isError: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource,
) {
    val transition = updateTransition(isError, "stateTransition")
    val borderColor = transition.animateColor(
        transitionSpec = { tween(durationMillis = AnimationDuration) },
        label = "borderColor",
    ) {
        when (it) {
            true -> OrbitTheme.colors.critical.normal
            false -> Color.Transparent
        }
    }

    Box(
        modifier
            .clip(OrbitTheme.shapes.normal)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            )
            .border(1.dp, borderColor.value, OrbitTheme.shapes.normal)
            .background(OrbitTheme.colors.surface.normal, OrbitTheme.shapes.normal),
        propagateMinConstraints = true,
    ) {
        FieldContent(
            fieldContent = { Text(value) },
            placeholder = if (value.isEmpty()) placeholder else null,
            leadingIcon = leadingIcon,
            onLeadingIconClick = null,
            trailingIcon = trailingIcon,
            onTrailingIconClick = null,
            singleLine = singleLine,
        )
    }
}

private const val AnimationDuration = 150

@OrbitPreviews
@Composable
internal fun ClickableFieldPreview() {
    Preview {
        ClickableField(
            value = "Attach filed",
            onClick = {},
            label = { Text("Attachment") },
            info = { Text("Attach JPEG.") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Attachment, contentDescription = null) },
            trailingIcon = { Icon(Icons.Close, contentDescription = null) },
        )

        ClickableField(
            value = "Attach filed",
            onClick = {},
            label = { Text("Attachment") },
            error = { Text("Wrong file type.") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Attachment, contentDescription = null) },
            trailingIcon = { Icon(Icons.Close, contentDescription = null) },
        )
    }
}
