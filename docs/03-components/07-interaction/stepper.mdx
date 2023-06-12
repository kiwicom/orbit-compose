---
title: Android
---

## Overview

`Stepper` component is provided as a single value component in two variants:

- [`Stepper` with min & max values](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-stepper.html)
- [`Stepper` with a custom validator](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-stepper.html)

## Usage

Use the `Stepper` composable and define its value and value change callback. Optionally, set up the value boundaries using `minValue` and `maxValue`. The overloaded implementation allows you to set up a custom validator and also set custom `contentDescription` labels for the increase and decrease actions.

The whole component can be info-color highlighted by setting it as `active`.

```kotlin
@Composable
fun Example() {
    var value by remember { mutableIntStateOf(0) }
    Stepper(
        modifier = Modifier.testTag("stepper"),
        value = value,
        onValueChange = { value = it },
        active = value > 0,
        minValue = 0,
        maxValue = 10,
    )
}
```

## UI Testing

Use semantic properties and custom actions to read the value and increase/decrease it.

If the button is not active, the particular semantic action is not added, use `fetchSemanticsNode()` to check its presence.

```kotlin
composeTestRule.setContent {
    var value by remember { mutableIntStateOf(0) }
    Stepper(
        modifier = Modifier.testTag("stepper"),
        value = value,
        onValueChange = { value = it },
    )
}
val stepper = composeTestRule.onNodeWithTag("stepper")

assertFalse(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.DecreaseValue))
assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.IncreaseValue))

stepper
    .assertTextEquals("0")
    .performSemanticsAction(StepperSemanticsActions.IncreaseValue)
    .assertTextEquals("1")
    .performSemanticsAction(StepperSemanticsActions.IncreaseValue)
    .assertTextEquals("2")
    .performSemanticsAction(StepperSemanticsActions.DecreaseValue)
    .assertTextEquals("1")

assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.DecreaseValue))
assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.IncreaseValue))
```

## Customization

`Stepper` is not customizable.
