package kiwi.orbit.compose.catalog.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.PasswordTextField
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TextField

@Composable
fun TextFieldScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Text Field",
        onUpClick = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            TextFieldScreenInner()
        }
    }
}

@Preview
@Composable
private fun TextFieldScreenInner() {
    Column(Modifier.padding(16.dp)) {
        val passwordFocusRequester = FocusRequester()
        val bioFocusRequester = FocusRequester()
        val nationalityFocusRequester = FocusRequester()

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
                    nationalityFocusRequester.requestFocus()
                },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
        )

        Spacer(Modifier.height(16.dp))

        var isNationalityError by remember { mutableStateOf(false) }
        var nationality by remember { mutableStateOf("") }
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
    }
}
