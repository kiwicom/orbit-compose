package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.Timeline
import kiwi.orbit.compose.ui.controls.TimelineItem
import kiwi.orbit.compose.ui.controls.TimelineItemStatus
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun TimelineScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timeline") },
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
            TimelineScreenInner()
        }
    }
}

@Composable
private fun TimelineScreenInner() {
    Column {
        var activeItem by rememberSaveable { mutableStateOf(1) }
        Timeline(
            Modifier
                .clickable { activeItem = (activeItem + 1).mod(6) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            fun resolveState(n: Int, i: Int): TimelineItemStatus = when {
                n == 4 && i - 1 == n -> TimelineItemStatus.CurrentSuccess
                i > n -> TimelineItemStatus.PastSuccess
                i == n -> TimelineItemStatus.InProgress
                else -> TimelineItemStatus.Future
            }
            TimelineItem(
                status = resolveState(1, activeItem),
                title = { Text("Check-in details") },
                description = { Text("To check you in, we need your passport/ID or some other details at least 3h before departure.") },
            )
            TimelineItem(
                status = resolveState(2, activeItem),
                title = { Text("Waiting for the airline") },
                description = { Text("We’ll wait for the airline to open their online check-in.") },
            )
            TimelineItem(
                status = resolveState(3, activeItem),
                title = { Text("Processing online check-in") },
                description = { Text("We’ll check you in with the airport and notify you when it’s finished.") },
            )
            TimelineItem(
                status = resolveState(4, activeItem),
                title = { Text("Boarding passes ready") },
                description = { Text("You’ll find your boarding passes here as soon as they’re ready at least 2h before your departure.") },
            )
        }
        Separator()
        Timeline(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
            )
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Waiting for the airline") },
            )
            TimelineItem(
                status = TimelineItemStatus.CurrentWarning,
                title = { Text("Processing online check-in") },
            )
            TimelineItem(
                status = TimelineItemStatus.Future,
                title = { Text("Boarding passes ready") },
            )
        }
        Separator()
        Timeline(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TimelineItem(
                status = TimelineItemStatus.PastSuccess,
                title = { Text("Check-in details") },
            )
            TimelineItem(
                status = TimelineItemStatus.PastWarning,
                title = { Text("Waiting for the airline") },
            )
            TimelineItem(
                status = TimelineItemStatus.CurrentCritical,
                title = { Text("Processing online check-in") },
            )
            TimelineItem(
                status = TimelineItemStatus.Future,
                title = { Text("Boarding passes ready") },
            )
        }
    }
}

@Preview
@Composable
private fun TimelineScreenPreview() {
    AppTheme {
        TimelineScreen(onNavigateUp = {})
    }
}
