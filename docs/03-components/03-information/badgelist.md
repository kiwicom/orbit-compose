---
title: Android
---

## Overview

`BadgeList` component is provided in two variants:

- [`BadgeList`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list.html)
- [`BadgeListSmall`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-small.html)

Particular items are provided in these predefined variants:

- [`BadgeListItemInfo`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-item-info.html)
- [`BadgeListItemSuccess`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-item-success.html)
- [`BadgeListItemWarning`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-item-warning.html)
- [`BadgeListItemCritical`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-item-critical.html)
- [`BadgeListItemNeutral`](https://kiwicom.github.io/orbit-compose/ui/kiwi.orbit.compose.ui.controls/-badge-list-item-neutral.html)

The `BadgeList` is independent from `Badge` component.

## Usage

Use `BadgeList` composable two wrap particular items. Each item takes an icon Painter and has a `content` slot.

```kotlin
@Composable
fun Example() {
    Column {
        BadgeList {
            BadgeListSuccess(Icons.Check) { Text("Your order is being processed right now.") }
            BadgeListInfo(Icons.Info) { Text("We will email you when there will be a progress.") }
            BadgeListInfo(Icons.Money) { Text("Additional credits were added to your account.") }
        }
    }
}
```

You can customize item spacing and the overall "text color" by using `ContentEmphasis`.

```kotlin
@Composable
fun Example() {
    Column {
        BadgeListSmall(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentEmphasis = ContentEmphasis.Minor,
        ) {
            BadgeListSuccess(Icons.Check) { Text("Your order is being processed right now.") }
            BadgeListInfo(Icons.Info) { Text("We will email you when there will be a progress.") }
            BadgeListInfo(Icons.Money) { Text("Additional credits were added to your account.") }
        }
    }
}
```

You can provide content description for the item's icon using `iconContentDescription` parameter.

## UI Testing

Slotting API allows you to add testTag to particular component parts.

```kotlin
composeTestRule.setContent {
    BadgeList {
        BadgeListItemNeutral(Icons.Email) {
            Text("Email up to date!", Modifier.testTag("Email"))
        }
    }
}
composeTestRule.onNodeWithTag("Email").assertTextContains("Email up to date!")
```

## Customization

`BadgeList` appearance is not customizable.
