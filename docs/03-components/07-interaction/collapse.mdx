---
title: Android
---

## Overview

`Collapse` Hides long or complex information under a block that can be hidden.

- [`Collapse`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-collapse.html)

## Usage

Use `Collapse` composable and define a boolean state as an argument for `expanded`. Process the value in `onExpandClick` callback.

`Collapse` has `title` slot for the always visible content. Use the `content` slot to provide the collapsible content.

```kotlin
@Composable
fun Example() {
    var expanded by remember { mutableStateOf(false) }

    Collapse(
        expanded = expanded,
        onExpandClick = { expanded = it },
        title = {
            Text(text = "Collapse title")
        },
        content = {
            Text(text = "This is the collapsible content")
        },
     )
}
```

Optionally you can choose to display the separator between the title and the content or not by setting `withSeparator`, the default value is true.

```kotlin
@Composable
fun Example() {
    var expanded by remember { mutableStateOf(false) }

    Collapse(
        expanded = expanded,
        onExpandClick = { expanded = it },
        title = {
            Text(text = "Collapse title")
        },
        content = {
            Text(text = "This is the collapsible content")
        },
        withSeparator = false,
     )
}
```

## UI Testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    var expanded by remember { mutableStateOf(false) }
    Collapse(
        expanded = expanded,
        onExpandClick = { expanded = it },
        title = {
            Text(text = "Collapse title", modifier = Modifier.testTag("title"))
        },
        content = {
            Text(text = "This is the collapsible content", modifier = Modifier.testTag("content"))
        },
        modifier = Modifier.testTag("collapse"),
    )
}

composeTestRule.onNodeWithTag("title", useUnmergedTree = true).assertIsDisplayed()
composeTestRule.onNodeWithTag("content").assertDoesNotExist()

composeTestRule.onNodeWithTag("collapse").performSemanticsAction(SemanticsActions.Expand)
composeTestRule.onNodeWithTag("content").assertIsDisplayed()

composeTestRule.onNodeWithTag("collapse").performSemanticsAction(SemanticsActions.Collapse)
composeTestRule.onNodeWithTag("content").assertDoesNotExist()
```

## Customization

`Collapse` appearance is not customizable.
