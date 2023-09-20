---
title: Android
---

## Overview

`Tab` component is provided as a simple wrapping layout and tab item components:

- [`TabRow`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-tab-row.html)
- [`ScrollableTabRow`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-scrollable-tab-row.html)
- [`Tab`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-tab.html)

## Usage

`TabRow` layout takes a `content` slot with multiple `Tab`s. `TabRow` should utilize `TopAppBar`'s `extraContent` slot.
`ScrollableTabRow` is a scrollable variant of the component, allowing for more tabs to be displayed off screen.

```kotlin
@Composable
fun Example() {
    var selectedPage by rememberSaveable { mutableStateOf(0) }
    TopAppBar(
        title = { /* ... */ },
        onNavigateUp = { /* ... */ },
        extraContent = {
            TabRow(selectedTabIndex = selectedPage) {
                Tab(
                    selected = selectedPage == 0,
                    onClick = { selectedPage = 0 },
                    text = { Text("Variant A") },
                )
                Tab(
                    selected = selectedPage == 1,
                    onClick = { selectedPage = 1 },
                    text = { Text("Variant B") },
                )
            }
        }
    )
}
```

## UI Testing

`Tab` composable provides a standard interface for UI testing, e.g. it exposes `isSelected` semantics property
 to check which tab is selected. If your tabs are in a `ScrollableTabRow` you should first call
 `performScrollTo()` before `performClick()` to make sure the tab is actually visible on screen.

```kotlin
composeTestRule.setContent {
    var selectedPage by remember { mutableStateOf(0) }
    ScrollableTabRow(selectedTabIndex = selectedPage) {
        Tab(
            modifier = Modifier.testTag("variantA"),
            selected = selectedPage == 0,
            onClick = { selectedPage = 0 },
            text = { Text("Variant A") },
        )
        Tab(
            modifier = Modifier.testTag("variantB"),
            selected = selectedPage == 1,
            onClick = { selectedPage = 1 },
            text = { Text("Variant B") },
        )
        Tab(
            modifier = Modifier.testTag("variantC"),
            selected = selectedPage == 2,
            onClick = { selectedPage = 2 },
            text = { Text("Variant C") },
        )
    }
}
composeTestRule.onNodeWithTag("variantC").performScrollTo().performClick().assertIsSelected()
```

## Customization

`TabRow`/`ScrollableTabRow`/`Tab` appearance is not customizable.
