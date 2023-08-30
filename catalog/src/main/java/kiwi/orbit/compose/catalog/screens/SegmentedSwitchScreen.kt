package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.SegmentedSwitch
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun SegmentedSwitchScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("SegmentedSwitch") },
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
            SegmentedSwitchScreenInner()
        }
    }
}

@Preview
@Composable
private fun SegmentedSwitchScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        var selectedIndexFirst by rememberSaveable { mutableStateOf<Int?>(null) }
        SegmentedSwitch(
            optionFirst = { Text("Male") },
            optionSecond = { Text("Female") },
            selectedIndex = selectedIndexFirst,
            onOptionClick = { index ->
                selectedIndexFirst = index.takeIf { index != selectedIndexFirst }
            },
            label = { Text("Gender") },
        )

        var selectedIndexSecond by rememberSaveable { mutableStateOf<Int?>(null) }
        SegmentedSwitch(
            options = listOf(
                { Text("Off") },
                { Text("On") },
                { Text("Remote") },
            ),
            selectedIndex = selectedIndexSecond,
            onOptionClick = { index ->
                selectedIndexSecond = index.takeIf { index != selectedIndexSecond }
            },
            label = { Text("Feature") },
        )

        var selectedIndexInfo by rememberSaveable { mutableStateOf<Int?>(null) }
        val infoList = listOf(
            "Pizza was invented in Naples, Italy.",
            "Burgers come from Hamburg, Germany.",
        )
        SegmentedSwitch(
            onOptionClick = { index ->
                selectedIndexInfo = index.takeIf { index != selectedIndexInfo }
            },
            options = listOf(
                { Text("Pizza") },
                { Text("Burgers") },
            ),
            selectedIndex = selectedIndexInfo,
            label = { Text("Food info") },
            info = selectedIndexInfo?.let { { Text(infoList[it]) } },
        )

        var selectedIndexError by rememberSaveable { mutableStateOf<Int?>(null) }
        SegmentedSwitch(
            onOptionClick = { index ->
                selectedIndexError = index.takeIf { index != selectedIndexError }
            },
            options = listOf(
                { Text("Star Trek") },
                { Text("Star Wars") },
                { Text("Stargate") },
            ),
            selectedIndex = selectedIndexError,
            label = { Text("Sci-fi shows") },
            error = if (selectedIndexError == null) {
                { Text("Please select an option.") }
            } else {
                null
            },
        )
    }
}
