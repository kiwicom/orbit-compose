---
title: Android
---

## Overview

Support for a multi-line text input (textarea) is provided
by [TextField composable](https://orbit.kiwi/components/input/inputfield/android/).

Simply utilize `singleLine`. Optionally you may set up the maximum of rendered lines by the `maxLines` argument.

```kotlin
@Composable
fun Example() {
    TextField(
        value = "...",
        onValueChange = {},
        singleLine = false,
        maxLines = 4,
    )
}
```
