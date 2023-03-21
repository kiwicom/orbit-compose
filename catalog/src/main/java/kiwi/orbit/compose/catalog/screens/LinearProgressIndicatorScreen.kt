package kiwi.orbit.compose.catalog.screens

import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.LinearIndeterminateProgressIndicator
import kiwi.orbit.compose.ui.controls.LinearProgressIndicator
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun LinearProgressIndicatorScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Linear Progress Indicator") },
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
            LinearProgressIndicatorScreenInner()
        }
    }
}

@Composable
private fun LinearProgressIndicatorScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        var progress by rememberSaveable { mutableStateOf(0.5f) }
        LinearIndeterminateProgressIndicator()
        LinearProgressIndicator(progress)
        LinearProgressIndicator(progress, progressAnimationSpec = snap())

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ButtonSecondary(
                onClick = { if (progress > 0.1f) progress -= 0.1f },
                modifier = Modifier
                    .weight(0.5f)
                    .alpha(if (progress > 0.1f) 1f else 0.3f),
            ) {
                Text("Decrease")
            }

            ButtonSecondary(
                onClick = { if (progress < 1f) progress += 0.1f },
                modifier = Modifier
                    .weight(0.5f)
                    .alpha(if (progress < 1f) 1f else 0.3f),
            ) {
                Text("Increase")
            }
        }
    }
}

@Preview
@Composable
private fun LinearProgressIndicatorScreenPreview() {
    AppTheme {
        LinearProgressIndicatorScreen(onNavigateUp = {})
    }
}
