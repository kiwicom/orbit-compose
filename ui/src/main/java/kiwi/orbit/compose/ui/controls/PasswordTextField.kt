package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

@Composable
public fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    strengthContent: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var showRawInput by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        error = error,
        info = info,
        additionalContent = {
            if (strengthContent != null) {
                Spacer(Modifier.height(4.dp))
                strengthContent()
            }
        },
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        onLeadingIconClick = onLeadingIconClick,
        trailingIcon = {
            Icon(
                when (showRawInput) {
                    true -> Icons.VisibilityOff
                    false -> Icons.Visibility
                },
                contentDescription = when (showRawInput) {
                    true -> stringResource(id = R.string.orbit_cd_text_field_hide_password)
                    false -> stringResource(id = R.string.orbit_cd_text_field_show_password)
                },
            )
        },
        onTrailingIconClick = { showRawInput = !showRawInput },
        keyboardOptions = keyboardOptions.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = when (showRawInput) {
            true -> VisualTransformation.None
            false -> PasswordVisualTransformation()
        },
        interactionSource = interactionSource,
    )
}

@OrbitPreviews
@Composable
internal fun PasswordTextFieldPreview() {
    Preview {
        PasswordTextField(
            value = "password",
            label = { Text("Password") },
            onValueChange = {},
            strengthContent = {
                PasswordStrengthIndicator(
                    value = 0.2f,
                    color = PasswordStrengthColor.Weak,
                    label = { Text(text = "Weak") },
                )
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Security, contentDescription = null) },
        )

        PasswordTextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") },
            placeholder = { Text("Your password") },
            error = { Text("Password doesn't match.") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Security, contentDescription = null) },
        )
    }
}
