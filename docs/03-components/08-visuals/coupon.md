---
title: Android
---

## Overview

`Coupon` is a simple component used for highlighting coupons / promo codes.

- [`Coupon`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-coupon.html)

## Usage

`Coupon` has `code` parameter that will be converted to uppercase and wrapped in a dashed border.

If you provide `onCopied` callback, the component will copy the displayed code to clipboard on long click.
You should provide adequate feedback to the user in reaction to this action. To disable this functionality,
pass `null` to the parameter.

```kotlin
@Composable
fun Example() {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = null,
                modifier = Modifier.alignByBaseline(),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Copy your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = {
                    coroutineScope.launch {
                        toastHostState.showToast("Copied to clipboard!") { Icons.Copy }
                    }
                },
                modifier = Modifier.alignByBaseline(),
            )
        }
    }
}
```

## UI Testing

Add `testTag` to the component to test its properties.

```kotlin
composeTestRule.setContent {
    Coupon(
        code = "hxt3b81f",
        onCopied = null,
        modifier = Modifier.testTag("coupon"),
    )
}
composeTestRule.onNodeWithTag("coupon").assertTextEquals("HXT3B81F")
```

## Customization

`Coupon` appearance is not customizable.
