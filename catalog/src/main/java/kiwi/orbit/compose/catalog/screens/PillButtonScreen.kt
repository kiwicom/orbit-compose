package kiwi.orbit.compose.catalog.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonCritical
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.PillButton
import kiwi.orbit.compose.ui.controls.PillButtonContainer
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun PillButtonScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PillButton") },
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
            PillButtonScreenScreenInner()
        }
    }
}

@Preview
@Composable
private fun PillButtonScreenScreenInner() {
    Column {
        PillButtonDemo()
        PillButtonDemo(
            modifier = Modifier.background(OrbitTheme.colors.surface.strong),
            showIcon = false,
            text = "Iconless PillButton",
        )
    }
}

@Composable
private fun PillButtonDemo(
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
    text: String = "2 new results",
) {
    var visible by remember { mutableStateOf(true) }
    PillButtonContainer(
        button = {
            PillButton(
                onClick = { visible = false },
                icon = if (showIcon) {
                    { Icon(painter = Icons.ArrowUp, contentDescription = null) }
                } else {
                    { }
                },
            ) {
                Text(text)
            }
        },
        buttonVisible = visible,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            val alpha by animateFloatAsState(targetValue = if (visible) 0.5f else 1f)

            ButtonCritical(
                onClick = {
                    if (!visible) {
                        visible = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha),
            ) {
                Text("Reset")
            }
        }
    }
}
