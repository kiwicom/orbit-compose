package kiwi.orbit.app.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.app.SubScreen
import kiwi.orbit.controls.PasswordTextField
import kiwi.orbit.controls.TextField
import kiwi.orbit.icons.Icons

@Composable
fun TextFieldScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Text Field",
        onUpClick = onUpClick,
    ) {
        TextFieldScreen()
    }
}

@Preview
@Composable
fun TextFieldScreen() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val passwordFocusRequester = FocusRequester()
            val bioFocusRequester = FocusRequester()

            var hasError by remember { mutableStateOf(false) }
            var email by remember { mutableStateOf("") }
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

            var password by remember { mutableStateOf("") }
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Security, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        bioFocusRequester.requestFocus()
                    },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
            )

            Spacer(Modifier.height(16.dp))

            var bio by remember { mutableStateOf("") }
            TextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                leadingIcon = { Icon(Icons.Linkedin, contentDescription = null) },
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(bioFocusRequester),
            )
        }
    }
}
