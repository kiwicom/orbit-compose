---
title: Android
---

## Overview

`Tile` component is elevated clickable box-item with a trailing indication of further action.

- [`Tile`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-tile.html)

## Usage

`Tile` comes in 2 variants:

1. The first variant provides slots for `title`, `icon`, and `description`. All with appropriate text styles provided.
2. The second variant provides just `content` slot where you can define any custom layout.

All variants come with `trailingContent` slot where you can define the indicator of further action.
Appropriate text style and content emphasis are provided.
This slot has a `RowScope` context in case you want to display more end-aligned elements, e.g. badges.
You can also vertically align each element using the `RowScope.align` modifier.
By default, the `trailingContent` displays forward chevron icon.

```kotlin
@Composable
fun Example() {
    Column {
        Tile(
            title = { Text("Title") },
            onClick = {},
            icon = { Icon(Icons.Airplane, contentDescription = null) },
            description = { Text("Description") },
            trailingContent = {
                Text(
                    text = "Action",
                    modifier = Modifier.align(Alignment.Top),
                    color = OrbitTheme.colors.primary.normal,
                    style = OrbitTheme.typography.bodyNormalMedium,
                )
            },
        )
        Tile(
            onClick = {},
        ) {
            Text(
                text = "Custom content",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
```

## UI Testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    Tile(
        title = { Text("Title", Modifier.testTag("title")) },
        onClick = {},
        modifier = Modifier.testTag("tile"),
        description = { Text("Description", Modifier.testTag("description")) },
    )
}

composeTestRule.onNodeWithTag("title").assertTextEquals("Title")
composeTestRule.onNodeWithTag("description").assertTextEquals("Description")
composeTestRule.onNodeWithTag("tile").performClick()
```

## Customization

`Tile` appearance is not customizable.
