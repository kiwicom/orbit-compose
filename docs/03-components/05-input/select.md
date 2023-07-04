---
title: Android
---

## Overview

`SelectField` component is provided as combination of the input and popup list:

- [`SelectField`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-select.html)

## Usage

`SelectField` API is similar to `TextField`, it accepts the value, `label`, `error` and `info` slots, and more. Pass the `List<T>` options and define their rendering using the `optionContent` slot. You may utilize `leadingIcon`, but it cannot be made clickable as the whole field is clickable and opens the popup list.

```kotlin
@Composable
fun Example() {
    var selected by remeber { mutableStateOf("A") }
    SelectField(
        value = selected,
        options = remember { listOf("A", "B", "C") },
        onOptionSelect = { selected = it },
        label = { Text("Pick a letter") }
    ) { letter ->
        Text(letter)
    }
}
```

## UI testing

The slotting API allows you to add `testTag` to particular options. Utilize the unmerged tree to click them. The select's value is exposed through the `EditableText` semantic property.

```kotlin
composeTestRule.setContent {
    var selected by remember { mutableStateOf("A") }
    SelectField(
        modifier = Modifier.testTag("select"),
        value = selected,
        options = listOf("A", "B", "C"),
        onOptionSelect = { selected = it },
        label = { Text("Pick letter") },
    ) { letter ->
        Text(letter, modifier = Modifier.testTag("option$letter"))
    }
}

val select = composeTestRule.onNodeWithTag("select")
select.assert(hasEditTextExactly("A"))
select.performClick()
composeTestRule.onNodeWithTag("optionB", useUnmergedTree = true).performClick()
select.assert(hasEditTextExactly("B"))

private fun hasEditTextExactly(expected: String): SemanticsMatcher {
    val propertyName = SemanticsProperties.EditableText.name
    return SemanticsMatcher(
        "$propertyName = [$expected]",
    ) { node ->
        val actual = node.config.getOrNull(SemanticsProperties.EditableText)
        actual?.text == expected
    }
}
```

## Customization

`SelectField` appearance is not customizable.
