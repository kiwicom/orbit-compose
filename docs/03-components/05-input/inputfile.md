---
title: Android
---

## Overview

InputFile orbit component is provided only as a visual representation of a clickable field with the almost same API as common TextField.
- [`ClickableField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-clickable-field.html)

## Usage

Define the text value and click action of the `ClickableField`.

```kotlin
@Composable
fun Example() {
    ClickableField(
        value = "Attach filed",
        onClick = { /* ... */ },
        label = { Text("Attachment") },
        info = { Text("Attach JPEG.") },
        leadingIcon = { Icon(Icons.Attachment, contentDescription = null) },
    )
}
```
