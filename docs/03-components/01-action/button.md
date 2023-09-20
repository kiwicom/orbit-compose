---
title: Android
---

## Overview

`Button` component is provided in preselected variants:

- [`ButtonPrimary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-primary.html)
- [`ButtonPrimarySubtle`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-primary-subtle.html)
- [`ButtonSecondary`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-secondary.html)
- [`ButtonCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-critical.html)
- [`ButtonCriticalSubtle`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-critical-subtle.html)
- [`ButtonBundleBasic`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-bundle-basic.html), [`ButtonBundleMedium`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-bundle-medium.html), [`ButtonBundleTop`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-button-bundle-top.html)

## Usage

`Button` takes a `content` slot, provide button's text there (e.g. a text, icon and text, or similar).

```kotlin
@Composable
fun Example() {
    Column {
        ButtonPrimary(onClick = { /* ... */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Click here")
        }
    }
}
```

## UI Testing

`Button` merges all nodes to a single node, exposing the content's text and the action on the merged node. Use the modifier parameter to pass a testTag.

```kotlin
var clicked = false
composeTestRule.setContent {
    ButtonPrimary(onClick = { clicked = true }, Modifier.testTag("Button")) {
        Text("Update")
    }
}

composeTestRule.onNodeWithTag("Button").assertTextContains("Update")
composeTestRule.onNodeWithTag("Button").performClick()
Assert.assertEquals(true, clicked)
```

## Customization

`Button` appearance is not customizable. Alternatively, use `ButtonPrimitive` or see how to change app's theme.
