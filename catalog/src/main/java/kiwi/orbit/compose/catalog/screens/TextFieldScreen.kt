package kiwi.orbit.compose.catalog.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.PasswordStrengthColor
import kiwi.orbit.compose.ui.controls.PasswordStrengthIndicator
import kiwi.orbit.compose.ui.controls.PasswordTextField
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TextField
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.controls.field.LabelLastBaseLine

@Composable
internal fun TextFieldScreen(onNavigateUp: () -> Unit) {
    var showAction by rememberSaveable { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Text Field") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = { showAction = !showAction }) {
                        Icon(
                            painter = when (showAction) {
                                true -> Icons.Visibility
                                false -> Icons.VisibilityOff
                            },
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        action = {
            if (showAction) {
                ButtonPrimary(onClick = {}) {
                    Text("Primary Action")
                }
            }
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            TextFieldScreenInner()
        }
    }
}

@Preview
@Composable
private fun TextFieldScreenInner() {
    Column(Modifier.padding(16.dp)) {
        val passwordFocusRequester = remember { FocusRequester() }
        val bioFocusRequester = remember { FocusRequester() }
        val nationalityFocusRequester = remember { FocusRequester() }

        var hasError by rememberSaveable { mutableStateOf(false) }
        var email by rememberSaveable { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = {
                email = it
                hasError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text("E-mail") },
            leadingIcon = { Icon(Icons.Email, contentDescription = null) },
            error = if (hasError) {
                { Text("Please use this format: your@email.com") }
            } else {
                null
            },
            placeholder = { Text("your@email.com") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                },
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))

        var password by rememberSaveable { mutableStateOf("") }
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Security, contentDescription = null) },
            strengthContent = {
                val (color, label) = when (password.length) {
                    in (0..4) -> PasswordStrengthColor.Weak to "Weak"
                    in (5..8) -> PasswordStrengthColor.Medium to "Fair"
                    else -> PasswordStrengthColor.Strong to "Strong"
                }

                AnimatedVisibility(visible = password.isNotEmpty()) {
                    PasswordStrengthIndicator(
                        value = (password.length / 10f).coerceAtMost(1f),
                        color = color,
                        label = { Text(label) },
                    )
                }
            },
            info = if (password.isNotEmpty()) {
                { Text("A password for your awesome account.") }
            } else {
                null
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    nationalityFocusRequester.requestFocus()
                },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
        )

        Spacer(Modifier.height(16.dp))

        var isNationalityError by rememberSaveable { mutableStateOf(false) }
        var nationality by rememberSaveable { mutableStateOf("") }
        TextField(
            value = nationality,
            onValueChange = {
                nationality = it
                isNationalityError = it.length < 3
            },
            label = { Text("Nationality") },
            leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
            error = if (isNationalityError) {
                { Text("Nationality must have at least 3 letters.") }
            } else {
                null
            },
            info = { Text("A nationality code or full name.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    bioFocusRequester.requestFocus()
                },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(nationalityFocusRequester),
        )

        Spacer(Modifier.height(16.dp))

        var bio by rememberSaveable { mutableStateOf("") }
        TextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            info = { Text("Three lines about you.") },
            leadingIcon = { Icon(Icons.Linkedin, contentDescription = null) },
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(bioFocusRequester),
        )

        Spacer(Modifier.height(32.dp))
        Text("Sizing")
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var v1 by rememberSaveable { mutableStateOf("") }
            TextField(v1, { v1 = it }, label = { Text("A") })

            var v2 by rememberSaveable { mutableStateOf("") }
            TextField(v2, { v2 = it }, label = { Text("B") })
        }

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var v1 by rememberSaveable { mutableStateOf("") }
            TextField(
                value = v1,
                onValueChange = { v1 = it },
                label = { Text("Very long label with many many words") },
                modifier = Modifier
                    .weight(1f)
                    .alignBy(LabelLastBaseLine),
            )

            var v2 by rememberSaveable { mutableStateOf("") }
            TextField(
                value = v2,
                onValueChange = { v2 = it },
                label = { Text("B") },
                error = { Text("Error") },
                modifier = Modifier
                    .weight(1f)
                    .alignBy(LabelLastBaseLine),
            )
        }

        Spacer(Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            var v1 by rememberSaveable { mutableStateOf("") }
            TextField(v1, { v1 = it }, label = { Text("Long label") }, modifier = Modifier.fillMaxWidth())

            var v2 by rememberSaveable { mutableStateOf("") }
            TextField(v2, { v2 = it }, label = { Text("Label") }, modifier = Modifier.fillMaxWidth())
        }
    }
}
