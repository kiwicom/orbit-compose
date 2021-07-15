package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.foundation.FeatureColors
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
fun ColorsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Colors",
        onUpClick = onUpClick,
    ) {
        Surface {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                ColorsScreenInner()
            }
        }
    }
}

@Preview
@Composable
fun ColorsScreenInner() {
    FeatureColors(OrbitTheme.colors.primary, name = "Primary")
    FeatureColors(OrbitTheme.colors.interactive, name = "Interactive")
    FeatureColors(OrbitTheme.colors.success, name = "Success")
    FeatureColors(OrbitTheme.colors.warning, name = "Warning")
    FeatureColors(OrbitTheme.colors.critical, name = "Critical")
}

@Composable
private fun FeatureColors(colors: FeatureColors, name: String) {
    Text(name, Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(colors.subtle, "$name Subtle")
                Color(colors.subtleAlt, "$name Subtle Alt")
            }
            Row {
                Color(colors.main, "$name Main")
                Color(colors.mainAlt, "$name Main Alt")
            }
            Row {
                Color(colors.strong, "$name Strong")
            }
        }
    }
}

@Composable
private fun RowScope.Color(color: Color, name: String) {
    Box(
        Modifier
            .background(color)
            .weight(1f)
            .height(52.dp)
    ) {
        Text(
            name,
            Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            color = contentColorFor(color),
            style = OrbitTheme.typography.bodySmall
        )
    }
}
