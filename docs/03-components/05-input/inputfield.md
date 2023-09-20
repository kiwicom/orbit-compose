---
title: Android
---

## Overview

`TextField` component is provided as Orbit Compose implementation of `Input Field` in Orbit design. It is available in two
flavors:

- [`TextField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-text-field.html)
- [`PasswordTextField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-password-text-field.html)

To customize Password field, you may want to show

- [`PasswordStrengthIndicator`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-password-strength-indicator.html)

`TextField` represents an atomic block of a label, an input and an info/error message.

## Usage

`TextField` accepts a text value and a callback for its change. Read how to [manage text field state](https://medium.com/androiddevelopers/effective-state-management-for-textfield-in-compose-d6e5b070fbe5). 
Provide a label. Optionally, you may pass a placeholder, leading & trailing icons with their respective callbacks, or an error or an info message.

```kotlin
@Composable
fun Example() {
    TextField(
        value = "...",
        onValueChange = { /* ... */ },
        label = { Text("Full name") },
    )
}
```

Pass a composable lambda if there is an error:

```kotlin
@Composable
fun Example() {
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = { value = it },
        error = if (value.length > 3) {
            @Composable { Text("Error") }
        } else {
            null
        },
    )
}
```

To utilize password strength indicator, use the `strengthContent` slot on `PasswordTextField` composable, evaluate the strength as a `0f <= strength <= 1f` and map this strength to predefined colors, alternatively pass custom colors.

```kotlin
@Composable
fun Example() {
    PasswordTextField(
        value = value,
        onValueChange = { value = it },
        strengthContent = {
            val strength = calcStrength(value)
            PasswordStrengthIndicator(
                modifier = Modifier.testTag("passwordStrength"),
                value = strength,
                color = when {
                    strength < 0.2f -> PasswordStrengthColor.Weak
                    strength < 0.6f -> PasswordStrengthColor.Weak
                    strength < 0.8f -> PasswordStrengthColor.Medium
                    else -> PasswordStrengthColor.Strong
                },
                label = { Text("Strength: $strength") },
            )
        },
    )
}
```

Utilize `LabelLastBaseLine` alignment line to vertically align multiple text fields.

```kotlin
@Composable
import kiwi.orbit.compose.ui.controls.field.LabelLastBaseLine

fun Example() {
    Row {
        TextField(Modifier.alignBy(LabelLastBaseLine), ...)
        TextField(Modifier.alignBy(LabelLastBaseLine), ...)
    }
}
```

## UI testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    var value by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.testTag("textfield"),
        value = value,
        onValueChange = { value = it },
        info = { Text("Info") },
        error = if (value.length > 3) {
            @Composable { Text("Error") }
        } else {
            null
        },
        label = { Text("Label") },
        placeholder = { Text("Placeholder") },
    )
}

val textField = composeTestRule.onNodeWithTag("textfield")
textField.assert(hasEditTextExactly(""))
textField.assertTextEquals("Label", "Placeholder", includeEditableText = false)
textField.performTextReplacement("text")
textField.assert(hasEditTextExactly("text"))

private fun hasEditTextExactly(expected: String): SemanticsMatcher {
    val propertyName = SemanticsProperties.EditableText.name
    return SemanticsMatcher(
        "$propertyName = [$expected]",
    ) { node ->
        val actual = node.config.getOrNull(SemanticsProperties.EditableText)
        actual?.text == expected
    }
}
```

## Customization

`TextField` appearance is not customizable.
