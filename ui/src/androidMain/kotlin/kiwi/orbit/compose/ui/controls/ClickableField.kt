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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.editableText
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
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
    error: @Composable () -> Unit = {},
    info: @Composable () -> Unit = {},
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var isError by remember { mutableStateOf(false) }
    val errorMessage = stringResource(R.string.orbit_field_default_error)
    ColumnWithMinConstraints(
        modifier.semantics(mergeDescendants = true) {
            editableText = AnnotatedString(value)
            if (isError) {
                error(errorMessage)
            }
        },
    ) {
        if (label != null) {
            FieldLabel(label)
        }

        ClickableFieldBox(
            value = value,
            isError = isError,
            onClick = onClick,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
        )

        FieldMessage(
            error = error,
            info = info,
            onErrorResolved = { isError = it },
            showInfo = true,
        )
    }
}

@Suppress("LongParameterList")
@Composable
internal fun ClickableFieldBox(
    value: String,
    isError: Boolean,
    onClick: () -> Unit,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    singleLine: Boolean,
    maxLines: Int,
    minLines: Int,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
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
            fieldContent = {
                Text(
                    text = value,
                    maxLines = maxLines,
                    minLines = minLines,
                    overflow = if (singleLine) TextOverflow.Ellipsis else TextOverflow.Clip,
                )
            },
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

        ClickableField(
            value = "LaGuardia, John F. Kennedy International, Ostrava, Brno, Prague",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.AirplaneTakeoff, contentDescription = null) },
        )
    }
}
