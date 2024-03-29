---
title: Android
---

## Overview

`Card` is a rectangular (not rounded) surface provided for content separation into sections.

- [`Card`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-card.html)

For rounded and elevated Material-like card see
[`SurfaceCard`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-surface-card.html).

## Usage

`Card` provides a slot for `content` that is by default surrounded with appropriate padding.
However, this padding can be modified using the `contentPadding` parameter.
This allows the `Card` to be combined with other components that handle padding on their own, e.g.
[`ListChoice`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-list-choice.html).

Optionally, you can make use of the `title` and `action` slots that together make up a header for the `Card`.
Appropriate text styles are provided. These slots are vertically aligned by their baselines.

```kotlin
@Composable
fun Example() {
    Column {
        Card(
            title = { Text("Card title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        ) {
            Text("Content")
        }
        Card(
            title = { Text("Card title") },
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Column {
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneTakeoff, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Takeoff")
                }
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneLanding, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Landing")
                }
            }
        }
    }
}
```

## UI Testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    Card(
        title = { Text("Card title", Modifier.testTag("title")) },
        action = { ButtonTextLinkPrimary("Action", onClick = {}, Modifier.testTag("action")) },
    ) {
        Text("Content", Modifier.testTag("content"))
    }
}

composeTestRule.onNodeWithTag("title").assertTextEquals("Card title")
composeTestRule.onNodeWithTag("action").assertTextEquals("Action")
composeTestRule.onNodeWithTag("content").assertTextEquals("Content")
```

## Customization

`Card` appearance is not customizable.
