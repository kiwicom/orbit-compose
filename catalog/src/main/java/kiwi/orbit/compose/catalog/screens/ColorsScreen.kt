package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.foundation.FeatureColors
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
fun ColorsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Colors",
        onUpClick = onUpClick,
        withBackground = false,
    ) {
        ColorsScreenInner()
    }
}

@Preview
@Composable
fun ColorsScreenInner() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        SurfaceColors()
        ContentColors()
        FeatureColors(OrbitTheme.colors.primary, name = "Primary")
        FeatureColors(OrbitTheme.colors.interactive, name = "Interactive")
        FeatureColors(OrbitTheme.colors.success, name = "Success")
        FeatureColors(OrbitTheme.colors.warning, name = "Warning")
        FeatureColors(OrbitTheme.colors.critical, name = "Critical")
    }
}

@Composable
private fun SurfaceColors() {
    Column {
        Title("Surface")

        val colors = OrbitTheme.colors.surface
        Card {
            Column {
                Row {
                    Color(colors.main, "Surface Main")
                    Color(colors.background, "Surface Background")
                }
                Row {
                    Color(colors.subtle, "Surface Subtle")
                    Color(colors.strong, "Surface Strong")
                }
                Row {
                    Color(colors.disabled, "Surface Disabled")
                }
            }
        }
    }
}

@Composable
private fun ContentColors() {
    Column {
        Title("Content")

        val colors = OrbitTheme.colors.content
        val contentColor = OrbitTheme.colors.surface.main
        Card {
            Column {
                Row {
                    Color(colors.normal, "Content Normal", contentColor)
                }
                Row {
                    Color(colors.minor, "Content Minor", contentColor)
                    Color(colors.subtle, "Content Subtle", contentColor)
                }
                Row {
                    Color(colors.disabled, "Content Disabled", contentColor)
                }
            }
        }
    }
}

@Composable
private fun FeatureColors(colors: FeatureColors, name: String) {
    Column {
        Title(name)

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
}

@Composable
private fun Title(name: String) {
    Text(name, Modifier.padding(bottom = 4.dp), style = OrbitTheme.typography.title3)
}

@Composable
private fun RowScope.Color(color: Color, name: String, contentColor: Color = Color.Unspecified) {
    Box(
        Modifier
            .background(color)
            .weight(1f)
            .height(52.dp)
    ) {
        Text(
            name,
            Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            color = contentColor.takeOrElse { contentColorFor(color) },
        )
    }
}
