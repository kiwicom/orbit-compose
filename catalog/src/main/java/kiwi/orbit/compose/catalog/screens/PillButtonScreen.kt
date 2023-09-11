package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.catalog.semantics.PillButtonScreenSemantics
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.PillButton
import kiwi.orbit.compose.ui.controls.PillButtonContainer
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun PillButtonScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("PillButton") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        PillButtonScreenInner(contentPadding)
    }
}

@Composable
private fun PillButtonScreenInner(
    contentPadding: PaddingValues,
) {
    var show by rememberSaveable { mutableStateOf(false) }
    var showIcon by rememberSaveable { mutableStateOf(false) }
    PillButtonContainer(
        modifier = Modifier.padding(contentPadding),
        button = {
            PillButton(
                onClick = { show = false },
                icon = if (showIcon) {
                    { Icon(painter = Icons.ArrowUp, contentDescription = null) }
                } else {
                    { }
                },
            ) {
                Text("2 new results")
            }
        },
        buttonVisible = show,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ButtonSecondary(
                onClick = {
                    show = true
                    showIcon = false
                },
            ) { Text("Show") }
            ButtonSecondary(
                onClick = {
                    show = true
                    showIcon = true
                },
                modifier = Modifier.testTag(PillButtonScreenSemantics.ShowWithIconButtonTag),
            ) { Text("Show with icon") }
        }
    }
}

@Preview
@Composable
private fun PillButtonScreenPreview() {
    AppTheme {
        PillButtonScreen(onNavigateUp = {})
    }
}
