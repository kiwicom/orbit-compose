---
title: Android
---

## Overview

`Slider` component is provided as a single float-value slider:

- [`Slider`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-slider.html)
- [`RangeSlider`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-range-slider.html)

## Usage

Use `Slider` composable and define a state for its value. Process the value in `onValueChanged` and `onValueChangeFinished` callbacks. Provide labels for start and end value, you can pass an empty lambda not to render them. Optionally, setup steps or a custom range.  

```kotlin
@Composable
fun Example() {
    val volumeValue by rememberSaveable {
        mutableStateOf(0.0f)
    }
    Slider(
        value = volumeValue,
        onValueChange = { volumeValue = it },
        onValueChangeFinished = { /* process volumeValue */ },
        steps = 20,
        startLabel = { Text("0") },
        endLabel = { Text("100") },
    )
}
```

Use `RangeSlider` with similar API to have two-value slider.

```kotlin
@Composable
fun Example() {
    val volumeValue by remember {
        mutableStateOf(0.0f..1.0f)
    }
    RangeSlider(
        value = volumeValue,
        onValueChange = { volumeValue = it },
        onValueChangeFinished = { /* process volumeValue */ },
        steps = 20,
        startLabel = { Text("0") },
        endLabel = { Text("100") },
    )
}
```

## UI Testing

Use semantic properties and an action to read the state and set the progress.

```kotlin
composeTestRule.setContent {
    var currentValue by rememberSaveable { mutableStateOf(0.0f) }
    Slider(
        value = currentValue,
        onValueChange = { currentValue = it },
        modifier = Modifier.testTag("slider"),
        startLabel = {},
        endLabel = {},
    )
}

composeTestRule.onNodeWithTag("slider")
    .performSemanticsAction(SemanticsActions.SetProgress) { it.invoke(0.5f) }

val progressBarInfo = composeTestRule.onNodeWithTag("slider")
    .fetchSemanticsNode()
    .config[SemanticsProperties.ProgressBarRangeInfo]

assertEquals(0.5f, progressBarInfo.current)
```

For `RangeSlider`, fetch the "thumbs" children and call read their state or set the action using them.

```kotlin
composeTestRule.setContent {
    var currentValue by rememberSaveable { mutableStateOf(0.0f) }
    RangeSlider(
        value = currentValue,
        onValueChange = { currentValue = it },
        modifier = Modifier.testTag("slider"),
        startLabel = {},
        endLabel = {},
    )
}

val sliderThumbs = composeTestRule.onAllNodes(
    hasParent(hasTestTag("slider")) and
        SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress),
)
val start = sliderThumbs[0]
val end = sliderThumbs[1]

start.performSemanticsAction(SemanticsActions.SetProgress) { it.invoke(0.5f) }
end.performSemanticsAction(SemanticsActions.SetProgress) { it.invoke(0.8f) }

val startProgressBarInfo = start
    .fetchSemanticsNode()
    .config[SemanticsProperties.ProgressBarRangeInfo]

assertEquals(0.5f, startProgressBarInfo.current)

val endProgressBarInfo = end
    .fetchSemanticsNode()
    .config[SemanticsProperties.ProgressBarRangeInfo]

assertEquals(0.8f, endProgressBarInfo.current)
```

## Customization

`Slider` and `RangeSlider` appearance is not customizable.
