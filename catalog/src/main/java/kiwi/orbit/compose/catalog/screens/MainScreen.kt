package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Input
import androidx.compose.material.icons.rounded.LabelImportant
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.SmartButton
import androidx.compose.material.icons.rounded.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.catalog.MainActions
import kiwi.orbit.compose.catalog.components.Scaffold
import kiwi.orbit.compose.catalog.components.TopAppBar
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Text
import androidx.compose.material.icons.Icons.Rounded as MaterialIcons

@Composable
fun MainScreen(
    actions: MainActions,
    onToggleTheme: () -> Unit,
) {
    val foundation = listOf<Triple<String, @Composable () -> Unit, () -> Unit>>(
        Triple("Colors", { Icon(MaterialIcons.Palette, null) }, actions::showColors),
        Triple("Icons", { Icon(Icons.Airplane, null) }, actions::showIcons),
        Triple("Illustrations", { Icon(Icons.Gallery, null) }, actions::showIllustrations),
        Triple("Typography", { Icon(MaterialIcons.FormatSize, null) }, actions::showTypography),
    )

    val controls = listOf<Triple<String, @Composable () -> Unit, () -> Unit>>(
        Triple("Alert", { Icon(Icons.Alert, null) }, actions::showAlert),
        Triple("Badge", { Icon(Icons.Deals, null) }, actions::showBadge),
        Triple("Button", { Icon(MaterialIcons.SmartButton, null) }, actions::showButton),
        Triple("Checkbox", { Icon(MaterialIcons.CheckBox, null) }, actions::showCheckbox),
        Triple("Radio", { Icon(Icons.CircleFilled, null) }, actions::showRadio),
        Triple("Stepper", { Icon(Icons.PlusCircle, null) }, actions::showStepper),
        Triple("Switch", { Icon(MaterialIcons.ToggleOn, null) }, actions::showSwitch),
        Triple("Tag", { Icon(MaterialIcons.LabelImportant, null) }, actions::showTag),
        Triple("Text Field", { Icon(MaterialIcons.Input, null) }, actions::showTextField),
    )

    val demos = listOf<Triple<String, @Composable () -> Unit, () -> Unit>>(
        Triple("Profile", { Icon(Icons.AccountCircle, null) }, actions::showXProfile),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orbit Compose Catalog") },
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.statusBars,
                ),
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(MaterialIcons.BrightnessMedium, contentDescription = null)
                    }
                }
            )
        },
        backgroundColor = OrbitTheme.colors.surface.background,
    ) {
        BoxWithConstraints {
            val columns = (maxWidth / 156.dp).toInt().coerceAtLeast(1)
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
            ) {
                Spacer(Modifier.size(16.dp))
                CardsList("Foundation", foundation, columns)
                CardsList("Controls", controls, columns)
                CardsList("Demos", demos, columns)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardsList(
    title: String,
    items: List<Triple<String, @Composable () -> Unit, () -> Unit>>,
    columns: Int,
) {
    Text(
        text = title,
        style = OrbitTheme.typography.title3,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
    Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        for (rowItems in items.chunked(columns)) {
            Row {
                Items(rowItems)
                val missingColumns = columns - rowItems.size
                if (missingColumns > 0) {
                    Spacer(Modifier.weight(missingColumns.toFloat()))
                }
            }
        }
    }
}

@Composable
private fun RowScope.Items(rowItems: List<Triple<String, @Composable () -> Unit, () -> Unit>>) {
    for (item in rowItems) {
        Item(item.first, item.second, item.third)
    }
}

@Composable
private fun RowScope.Item(title: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    Card(
        Modifier
            .padding(4.dp)
            .weight(1f),
    ) {
        Row(
            Modifier
                .clip(OrbitTheme.shapes.normal)
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Spacer(Modifier.size(8.dp))
            Text(text = title, style = OrbitTheme.typography.title4)
        }
    }
}
