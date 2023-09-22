---
title: Android
---

## Overview

`ButtonLink` and `ButtonTextLink` components are provided in preselected variants:

- [`ButtonLinkPrimary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-link-primary.html)
- [`ButtonLinkSecondary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-link-secondary.html)
- [`ButtonLinkCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-link-critical.html)

and

- [`ButtonTextLinkPrimary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-text-link-primary.html)
- [`ButtonTextLinkSecondary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-text-link-secondary.html)
- [`ButtonTextLinkCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-text-link-critical.html)

## Usage

`ButtonLink*` is a full-width button for major action, yet not a primary CTAs. Its boundaries are similar to Orbit Button. On the other hand, `ButtonTextLink*` is a simple "text" that can be aligned to other composables quite easily. Its height is a bit bigger, yet the touch response (ripple effect) is drawn outside its boundaries.

```kotlin
@Composable
fun Example() {
    Column {
        ButtonLinkPrimary(onClick = { /* ... */ }) {
            Text("Click here")
        }

        ButtonTextLinkPrimary("Translate", onClick = { /* ... */ })
    }
}
```

## UI Testing

`ButtonLink` merges all nodes to a single node, exposing the content's text and the action on the merged node. Use the modifier parameter to pass a testTag.

```kotlin
var clicked1 = false
var clicked2 = false
composeTestRule.setContent {
    Column {
        ButtonLinkPrimary(onClick = { clicked1 = true }, Modifier.testTag("Button1")) {
            Text("Update1")
        }
        ButtonTextLinkPrimary("Update2", onClick = { clicked2 = true }, Modifier.testTag("Button2"))
    }
}

composeTestRule.onNodeWithTag("Button1").assertTextContains("Update1")
composeTestRule.onNodeWithTag("Button1").performClick()
Assert.assertEquals(true, clicked1)

composeTestRule.onNodeWithTag("Button2").assertTextContains("Update2")
composeTestRule.onNodeWithTag("Button2").performClick()
Assert.assertEquals(true, clicked2)
```

## Customization

`ButtonLinks*` appearance is not customizable.
