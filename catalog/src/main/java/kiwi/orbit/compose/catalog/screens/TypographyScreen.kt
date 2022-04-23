package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kotlin.math.roundToInt

@Composable
fun TypographyScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Typography") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            TypographyScreenInner()
        }
    }
}

@Preview
@Composable
private fun TypographyScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        val short = "Something descriptive and longer for more lines."
        val long = "Something with many many words, descriptive and longer for more lines."

        Typography("Title 1", OrbitTheme.typography.title1, short)
        Typography("Title 2", OrbitTheme.typography.title2, short)
        Typography("Title 3", OrbitTheme.typography.title3, short)
        Typography("Title 4", OrbitTheme.typography.title4, short)
        Typography("Title 5", OrbitTheme.typography.title5, long)
        Typography("Title 6", OrbitTheme.typography.title6, long.uppercase())

        Spacer(Modifier.size(16.dp))

        Typography("Body Extra Large", OrbitTheme.typography.bodyExtraLarge, short)
        Typography("Body Large", OrbitTheme.typography.bodyLarge, long)
        Typography("Body Normal", OrbitTheme.typography.bodyNormal, long)
        Typography("Body Small", OrbitTheme.typography.bodySmall, long)
    }
}

@Composable
private fun Typography(name: String, style: TextStyle, testText: String) {
    Row {
        Column(Modifier.weight(1f)) {
            Text(name.uppercase(), emphasis = ContentEmphasis.Subtle)
            Text(
                text = testText,
                modifier = Modifier.padding(end = 16.dp),
                style = style,
            )
        }
        Text(
            text = "${style.fontSize.value.roundToInt()}/${style.lineHeight.value.roundToInt()}",
            emphasis = ContentEmphasis.Subtle,
        )
    }
}
