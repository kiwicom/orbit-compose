---
title: Android
---

## Overview

`Checkbox` component is provided in two flavors, as a simple checkbox and as a row with an additional label and a description:

- [`Checkbox`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-checkbox.html) - a simple checkbox
- [`CheckboxField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-checkbox-field.html) a checkbox with a label and a description

## Usage

`Checkbox` accepts a `Boolean` state and a callback on its change.

```kotlin
@Composable
fun Example() {
    Checkbox(
        checked = false,
        onCheckedChange = { /*...*/ },
    )
}
```

`CheckboxField` has two additional slots - `description` and `label`.

```kotlin
@Composable
fun Example() {
    CheckboxField(
        checked = false,
        onCheckedChange = {},
        description = { Text("If selected, you will get the latest updates.") }
    ) {
        Text("Autoupdate")
    }
}
```

## UI testing

Slotting API allows you to add testTag to particular component parts. Utilize `assertIsOn` and `assertIsOff` to check the state.

```kotlin
composeTestRule.setContent {
    var checked by remember { mutableStateOf(false) }
    CheckboxField(
        modifier = Modifier.testTag("checkbox"),
        checked = checked,
        onCheckedChange = { checked = !checked },
    ) {
        Text("Label")
    }
}

val checkbox = composeTestRule.onNodeWithTag("checkbox")
checkbox.assertIsOff()
checkbox.performClick()
checkbox.assertIsOn()
```

## Customization

`Checkbox` and `CheckboxField` appearance is not customizable.
