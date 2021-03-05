package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.SubScreen

@Composable
fun TypographyScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Typography",
        onUpClick = onUpClick,
    ) {
        Surface() {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TypographyScreen()
            }
        }
    }
}

@Preview
@Composable
fun TypographyScreen() {
    Text("Display Title", style = OrbitTheme.typography.displayTitle)
    Text("Display Subtitle", style = OrbitTheme.typography.displaySubtitle)
    Text("Title 1", style = OrbitTheme.typography.title1)
    Text("Title 2", style = OrbitTheme.typography.title2)
    Text("Title 3", style = OrbitTheme.typography.title3)
    Text("Title 4", style = OrbitTheme.typography.title4)
    Text("Title 5", style = OrbitTheme.typography.title5)
    Text("Body Large", style = OrbitTheme.typography.bodyLarge)
    Text("Body Normal", style = OrbitTheme.typography.bodyNormal)
    Text("Body Small", style = OrbitTheme.typography.bodySmall)
}
