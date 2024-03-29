---
title: Android
---

## Overview

`Alert` component is provided in preselected variants:

- [`AlertInfo`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-info.html)
- [`AlertSuccess`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-success.html)
- [`AlertWarning`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-warning.html)
- [`AlertCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-critical.html)

You can render `Alert` in two variants: with tinted background or with neutral background - use `suppressed` parameter to disable the color.

If your content is short, there is another variant called `AlertInline`, it comes with the same `suppressed` variant, but one single action is required.

- [`AlertInlineInfo`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-inline-info.html)
- [`AlertInlineSuccess`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-inline-success.html)
- [`AlertInlineWarning`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-inline-warning.html)
- [`AlertInlineCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-alert-inline-critical.html)

## Usage

`Alert` has a `content` slot for the "description" part. Use the `title` slot to provide a title. Actions should be passed via this `actions` slot. To define an action, use `ButtonPrimary` or `ButtonSecondary` or both. Buttons will be automatically re-colored to match the `Alert` color "theme".

Each variant has a predefined icon, but you can change it using the `icon` parameter accepting a `Painter`.

```kotlin
@Composable
fun Example() {
    Column {
        AlertSuccess(
            title = { Text("Thank you for subscribing") },
            content = { Text("Your subscription was successfully activated. All premium content is now available in your account.") },
            actions = {
                ButtonPrimary(onClick = {}) { Text("Show content") }
                ButtonSecondary(onClick = {}) { Text("Manage subscription") }
            },
        )
        AlertInfo(
            icon = Icons.Check,
            content = { Text("You information is up-to-date.") },
        )
    }
}
```

`AlertInline` is suited for quick & short messages.

```kotlin
@Composable
fun Example() {
    Column {
        AlertInlineSuccess(
            title = { Text("Password changed") },
            action = { ButtonTextLinkPrimary("Account", onClick = { /* ... */ }) },
        )
    }
}
```

## UI testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    AlertSuccess(
        title = {},
        content = { Text("Thank you for subscribing") },
        actions = {
            ButtonPrimary(onClick = {}, Modifier.testTag("action")) {
                Text("Show content")
            }
        },
    )
}

composeTestRule.onNodeWithTag("action").performClick()
```

## Customization

`Alert` and `AlertInline` appearance is not customizable.
