package kiwi.orbit.app.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Input
import androidx.compose.material.icons.rounded.SmartButton
import androidx.compose.material.icons.rounded.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.InsetAwareTopAppBar
import kiwi.orbit.app.MainActions
import kiwi.orbit.icons.Icons
import androidx.compose.material.icons.Icons.Rounded as MaterialIcons

@Composable
fun MainScreen(
    actions: MainActions,
    onToggleTheme: () -> Unit,
) {

    val foundation = listOf<Triple<String, Any, () -> Unit>>(
        Triple("Typography", MaterialIcons.FormatSize, actions::showTypography),
    )

    val controls = listOf<Triple<String, Any, () -> Unit>>(
        Triple("Alert", Icons.Alert, actions::showAlert),
        Triple("Badge", Icons.Deals, actions::showBadge),
        Triple("Buttons", MaterialIcons.SmartButton, actions::showButton),
        Triple("Stepper", Icons.PlusCircle, actions::showStepper),
        Triple("Switch", MaterialIcons.ToggleOn, actions::showSwitch),
        Triple("TextField", MaterialIcons.Input, actions::showTextField),
    )

    val demos = listOf(
        Triple("Profile", Icons.AccountCircle, actions::showXProfile),
    )

    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text("Orbit Compose Demo") },
                backgroundColor = OrbitTheme.colors.surface,
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(MaterialIcons.BrightnessMedium, contentDescription = null)
                    }
                }
            )
        },
        backgroundColor = OrbitTheme.colors.surfaceBackground,
    ) {

        Column {

            Spacer(Modifier.size(16.dp))
            CardsList("Foundation", foundation)
            CardsList("Controls", controls)
            CardsList("Demos", demos)

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardsList(
    title: String,
    items: List<Triple<String, Any, () -> Unit>>
) {
    Column {
        Text(
            text = title,
            style = OrbitTheme.typography.title3,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
        LazyVerticalGrid(
            cells = GridCells.Adaptive(156.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(items) { (title, icon, onClick) ->
                Card(
                    Modifier.padding(4.dp),
                ) {
                    Row(
                        Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable(onClick = onClick)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (icon is Painter) {
                            Icon(painter = icon, contentDescription = null)
                        } else if (icon is ImageVector) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                        Spacer(Modifier.size(8.dp))
                        Text(text = title, style = OrbitTheme.typography.title4)
                    }
                }
            }
        }
    }
}
