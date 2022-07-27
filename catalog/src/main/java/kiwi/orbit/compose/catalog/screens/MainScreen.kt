package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material.icons.rounded.Announcement
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Ballot
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.EditAttributes
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material.icons.rounded.LabelImportant
import androidx.compose.material.icons.rounded.MenuOpen
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.SignalWifiOff
import androidx.compose.material.icons.rounded.SmartButton
import androidx.compose.material.icons.rounded.ToggleOn
import androidx.compose.material.icons.rounded.WebAsset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.MainActions
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBarLarge
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.utils.plus
import androidx.compose.material.icons.Icons.Outlined as OutlinedMaterialIcons
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
        Triple("Cards / Tiles", { Icon(MaterialIcons.Article, null) }, actions::showCards),
        Triple("Checkbox", { Icon(MaterialIcons.CheckBox, null) }, actions::showCheckbox),
        Triple("Choice Tile", { Icon(MaterialIcons.Ballot, null) }, actions::showChoiceTile),
        Triple("Dialogs", { Icon(Icons.Chat, null) }, actions::showDialogs),
        Triple("EmptyState", { Icon(MaterialIcons.SignalWifiOff, null) }, actions::showEmptyState),
        Triple("KeyValue", { Icon(MaterialIcons.DragHandle, null) }, actions::showKeyValue),
        Triple("ListChoice", { Icon(Icons.MenuHamburger, null) }, actions::showListChoice),
        Triple("Loading", { Icon(Icons.MenuMeatballs, null) }, actions::showLoading),
        Triple(
            "PillButton",
            { Icon(MaterialIcons.EditAttributes, null) },
            actions::showPillButton,
        ),
        Triple(
            "Progress Indicator",
            { Icon(OutlinedMaterialIcons.ToggleOff, null) },
            actions::showLinearProgressIndicator,
        ),
        Triple("Radio", { Icon(Icons.CircleFilled, null) }, actions::showRadio),
        Triple("Seat", { Icon(Icons.Seat, null) }, actions::showSeat),
        Triple("Select Field", { Icon(MaterialIcons.MenuOpen, null) }, actions::showSelectField),
        Triple("Stepper", { Icon(Icons.PlusCircle, null) }, actions::showStepper),
        Triple("Switch", { Icon(MaterialIcons.ToggleOn, null) }, actions::showSwitch),
        Triple("Tag", { Icon(MaterialIcons.LabelImportant, null) }, actions::showTag),
        Triple("Text Field", { Icon(MaterialIcons.Keyboard, null) }, actions::showTextField),
        Triple("Toast", { Icon(MaterialIcons.Announcement, null) }, actions::showToast),
        Triple("TopAppBar", { Icon(MaterialIcons.WebAsset, null) }, { actions.showTopAppBar() }),
    )

    Scaffold(
        topBar = {
            TopAppBarLarge(
                title = { Text("Orbit Compose Catalog") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(MaterialIcons.BrightnessMedium, contentDescription = null)
                    }
                },
            )
        },
        backgroundColor = OrbitTheme.colors.surface.background,
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = contentPadding + PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            cardItems("Foundation", foundation)
            cardItems("Controls", controls)
        }
    }
}

private fun LazyGridScope.cardItems(
    title: String,
    items: List<Triple<String, @Composable () -> Unit, () -> Unit>>,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Text(
            text = title,
            style = OrbitTheme.typography.title3,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
    items(items) { item ->
        Item(item.first, item.second, item.third)
    }
}

@Composable
private fun Item(title: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                icon()
                Spacer(Modifier.size(8.dp))
                Text(title)
            }
        }
    }
}
