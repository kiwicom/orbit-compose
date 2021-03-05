package kiwi.orbit.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.SubScreen
import kiwi.orbit.foundation.contentColorFor

@Composable
fun ColorsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Colors",
        onUpClick = onUpClick,
    ) {
        Surface() {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                ColorsScreen()
            }
        }
    }
}

@Preview
@Composable
fun ColorsScreen() {
    Text("Primary", Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(OrbitTheme.colors.primarySubtle, "Primary Subtle")
                Color(OrbitTheme.colors.primaryAltSubtle, "Primary Alt Subtle")
            }
            Row {
                Color(OrbitTheme.colors.primary, "Primary")
                Color(OrbitTheme.colors.primaryAlt, "Primary Alt")
            }
            Row {
                Color(OrbitTheme.colors.primaryStrong, "Primary Strong")
            }
        }
    }

    Text("Interactive", Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(OrbitTheme.colors.interactiveSubtle, "Interactive Subtle")
                Color(OrbitTheme.colors.interactiveAltSubtle, "Interactive Alt Subtle")
            }
            Row {
                Color(OrbitTheme.colors.interactive, "Interactive")
                Color(OrbitTheme.colors.interactiveAlt, "Interactive Alt")
            }
            Row {
                Color(OrbitTheme.colors.interactiveStrong, "Interactive Strong")
            }
        }
    }

    Text("Success", Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(OrbitTheme.colors.successSubtle, "Success Subtle")
                Color(OrbitTheme.colors.successAltSubtle, "Success Alt Subtle")
            }
            Row {
                Color(OrbitTheme.colors.success, "Success")
                Color(OrbitTheme.colors.successAlt, "Success Alt")
            }
            Row {
                Color(OrbitTheme.colors.successStrong, "Success Strong")
            }
        }
    }

    Text("Warning", Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(OrbitTheme.colors.warningSubtle, "Warning Subtle")
                Color(OrbitTheme.colors.warningAltSubtle, "Warning Alt Subtle")
            }
            Row {
                Color(OrbitTheme.colors.warning, "Warning")
                Color(OrbitTheme.colors.warningAlt, "Warning Alt")
            }
            Row {
                Color(OrbitTheme.colors.warningStrong, "Warning Strong")
            }
        }
    }

    Text("Critical", Modifier.padding(top = 16.dp, bottom = 4.dp), style = OrbitTheme.typography.title4)

    Card {
        Column {
            Row {
                Color(OrbitTheme.colors.criticalSubtle, "Critical Subtle")
                Color(OrbitTheme.colors.criticalAltSubtle, "Critical Alt Subtle")
            }
            Row {
                Color(OrbitTheme.colors.critical, "Critical")
                Color(OrbitTheme.colors.criticalAlt, "Critical Alt")
            }
            Row {
                Color(OrbitTheme.colors.criticalStrong, "Critical Strong")
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

