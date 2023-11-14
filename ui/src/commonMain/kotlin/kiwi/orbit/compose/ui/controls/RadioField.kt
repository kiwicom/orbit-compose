package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.layout.alignByLineHeight
import kiwi.orbit.compose.ui.utils.Strings
import kiwi.orbit.compose.ui.utils.getString

@Composable
public fun RadioField(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    description: (@Composable () -> Unit)? = null,
    label: @Composable ColumnScope.() -> Unit,
) {
    val selectable = if (onClick != null) {
        Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            )
    } else {
        Modifier
    }
    val errorMessage = getString(Strings.FieldDefaultError)
    Row(
        modifier = modifier
            .then(selectable)
            .semantics {
                if (isError) this.error(errorMessage)
            }
            .padding(contentPadding),
    ) {
        Radio(
            selected = selected,
            onClick = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .alignByLineHeight(OrbitTheme.typography.title5),
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
        )
        Column {
            CompositionLocalProvider(
                LocalTextStyle provides OrbitTheme.typography.title5,
                LocalContentColor provides OrbitTheme.colors.content.normal,
                LocalContentEmphasis provides if (enabled) ContentEmphasis.Normal else ContentEmphasis.Disabled,
            ) {
                label()
            }
            if (description != null) {
                CompositionLocalProvider(
                    LocalTextStyle provides OrbitTheme.typography.bodySmall,
                    LocalContentColor provides OrbitTheme.colors.content.normal,
                    LocalContentEmphasis provides if (enabled) ContentEmphasis.Minor else ContentEmphasis.Disabled,
                ) {
                    description()
                }
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun RadioFieldPreview() {
    Preview {
        RadioField(selected = false, onClick = {}) { Text("Text") }
        RadioField(selected = true, onClick = {}) { Text("Text") }
        RadioField(selected = false, onClick = {}, isError = true) { Text("Text") }
        RadioField(selected = true, onClick = {}, isError = true) { Text("Text") }
        RadioField(selected = false, onClick = {}, description = { Text("Description") }) {
            Text("Text")
        }
    }
}
