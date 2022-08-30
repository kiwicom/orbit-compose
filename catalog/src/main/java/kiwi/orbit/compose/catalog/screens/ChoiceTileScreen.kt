package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.components.CustomPlaceholder
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.controls.BadgeInfoSubtle
import kiwi.orbit.compose.ui.controls.BadgeNeutral
import kiwi.orbit.compose.ui.controls.ChoiceTile
import kiwi.orbit.compose.ui.controls.ChoiceTileCentered
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Stepper
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun ChoiceTileScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChoiceTile") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            ChoiceTileScreenInner()
        }
    }
}

@Preview
@Composable
private fun ChoiceTileScreenInner() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BoxWithConstraints {
            val width = maxWidth * 0.8f
            var mainSelection by remember { mutableStateOf(-1) }

            // Vertical padding to avoid clip issues on Android 12+ when over-scrolling.
            // https://issuetracker.google.com/issues/215652703
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            ) {
                ChoiceTile(
                    modifier = Modifier
                        .requiredWidth(width)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp),
                    selected = mainSelection == 0,
                    onSelect = { mainSelection = 0 },
                    title = { Text("Card 0") },
                    description = { Text("Some description") },
                    content = { },
                    footer = { Text("€20.3") },
                )

                ChoiceTile(
                    modifier = Modifier
                        .requiredWidth(width)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp),
                    selected = mainSelection == 1,
                    onSelect = { mainSelection = 1 },
                    title = { Text("Card 1") },
                    description = { Text("Some description") },
                    content = { CustomPlaceholder() },
                    footer = { Text("€20.3") },
                )
            }
        }

        var count by remember { mutableStateOf(0) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = {
                Text(
                    "Multiline long choice title label",
                    modifier = Modifier.weight(1f),
                )
                BadgeInfoSubtle {
                    Text("Popular")
                }
            },
            description = { Text("Multiline and very long description and lot of words") },
            icon = { Icon(painter = Icons.BaggageSet, contentDescription = null) },
            selected = count > 0,
            onSelect = {
                if (count == 0) {
                    count = 1
                }
            },
            showRadio = count == 0,
            footer = {
                Row(
                    modifier = Modifier.heightIn(min = 32.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        "$16.90",
                        Modifier.weight(1f),
                    )

                    if (count > 0) {
                        Stepper(
                            value = count,
                            onValueChange = { count = it },
                        )
                    }
                }
            },
        ) {
            CustomPlaceholder()
        }

        var selectedSmall by remember { mutableStateOf(false) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = {
                Text(
                    "Multiline long choice title label",
                    modifier = Modifier.weight(1f),
                )
            },
            description = { Text("Multiline and very long description and lot of words") },
            icon = { Icon(painter = Icons.BaggageSet, contentDescription = null) },
            selected = selectedSmall,
            onSelect = { selectedSmall = !selectedSmall },
            showRadio = true,
            largeHeading = false,
        )

        var selectedA by remember { mutableStateOf(false) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = { Text("ChoiceTile") },
            selected = selectedA,
            onSelect = { selectedA = !selectedA },
        ) {
            CustomPlaceholder()
        }

        var selectedB by remember { mutableStateOf(false) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            selected = selectedB,
            onSelect = { selectedB = !selectedB },
        ) {
            CustomPlaceholder()
        }

        var selectedC by remember { mutableStateOf(false) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = { Text("Simple title") },
            description = { Text("Simple description") },
            selected = selectedC,
            onSelect = { selectedC = !selectedC },
            content = { },
        )

        var selectedD by remember { mutableStateOf(false) }
        ChoiceTile(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = { Text("ChoiceTile with error") },
            description = { Text("Simple description") },
            selected = selectedD,
            isError = !selectedD,
            onSelect = { selectedD = !selectedD },
            content = { },
        )

        // Vertical padding to avoid clip issues on Android 12+ when over-scrolling.
        // https://issuetracker.google.com/issues/215652703
        var selectedE by remember { mutableStateOf(-1) }
        Row(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp) // half of the badge
                .padding(top = 2.dp, bottom = 4.dp), // over-scroll workaround
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ChoiceTileCentered(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                selected = selectedE == 0,
                onSelect = { selectedE = 0 },
                title = { Text("Plus Support") },
                description = { Text("Everyone sits together") },
                price = { BadgeNeutral { Text("Included") } },
            )

            ChoiceTileCentered(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                selected = selectedE == 1,
                onSelect = { selectedE = 1 },
                icon = {
                    Image(
                        painter = Illustrations.Boarding,
                        contentDescription = null,
                        Modifier.height(60.dp),
                    )
                },
                title = { Text("Plus Support") },
                description = { Text("Everyone sits together") },
                price = { Text("+ 10 €") },
            )

            ChoiceTileCentered(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                selected = selectedE == 2,
                onSelect = { selectedE = 2 },
                badgeContent = {
                    Text(
                        "Recommended Very Much",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                icon = { Icon(painter = Icons.BaggageSet, contentDescription = null) },
                title = { Text("Plus Support") },
                description = { Text("Everyone sits together") },
                price = { Text("+ 10 €") },
                largeHeading = false,
            )
        }
    }
}
