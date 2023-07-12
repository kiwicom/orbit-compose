---
title: Android
---

## Overview

`Radio` component is provided in two flavors, as a simple radio and as a row with an additional label and a
description:

- [`Radio`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-radio.html) -  simple radio
- [`RadioField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-radio-field.html) a radio with label and description

## Usage

`Checkbox` accepts a boolean state and a callback on its change.

```kotlin
@Composable
fun Example() {
    Radio(
        selected = false,
        onClick = { /*...*/ },
    )
}
```

`RadioField` has two additional slots - a `description` and a `label`.

```kotlin
@Composable
enum class TvShow { StarTrek, StarWars }

fun Example() {
    var tvshow by remember { mutableStateOf(TvShow.StarTrek) }
    Column {
        RadioField(
            selected = tvshow == TvShow.StarTrek,
            onClick = { tvshow = TvShow.StarTrek },
            description = { Text("Engage.") }
        ) {
            Text("StarTrek")
        }
        RadioField(
            selected = tvshow == TvShow.StarWars,
            onClick = { tvshow = TvShow.StarWars },
            description = { Text("May the force be with you.") }
        ) {
            Text("StarWars")
        }
    }
}
```

## UI testing

The Slotting API allows you to add a `testTag` to particular component parts. Utilize `assertIsSelected`
and `assertIsNotSelected` to check the state.

```kotlin
composeTestRule.setContent {
    var selected by remember { mutableStateOf(false) }
    RadioField(
        modifier = Modifier.testTag("radio"),
        selected = selected,
        onClick = { selected = !selected },
        description = { Text("Description") },
    ) {
        Text("Label")
    }
}

val radio = composeTestRule.onNodeWithTag("radio")
radio.assertIsNotSelected()
radio.performClick()
radio.assertIsSelected()
```

## Customization

`Radio` and `RadioField` appearance is not customizable.
