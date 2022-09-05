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
import androidx.compose.material.icons.rounded.DensitySmall
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kiwi.navigationcompose.typed.Route
import com.kiwi.navigationcompose.typed.toRoute
import kiwi.orbit.compose.catalog.Destinations
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
import kotlinx.serialization.ExperimentalSerializationApi
import androidx.compose.material.icons.Icons.Outlined as OMIcons
import androidx.compose.material.icons.Icons.Rounded as MIcons

private data class MenuItem(
    val title: String,
    val icon: Any,
    val onClick: () -> Unit,
)

@ExperimentalSerializationApi
@Composable
internal fun MainScreen(
    onNavigate: (Route) -> Unit,
    onToggleTheme: () -> Unit,
) {
    fun MenuItem(title: String, icon: Any, onNavigate: () -> Route): MenuItem =
        MenuItem(title, icon, onClick = { onNavigate(onNavigate()) })

    val foundation = listOf(
        MenuItem("Colors", MIcons.Palette) { Destinations.Colors.toRoute() },
        MenuItem("Icons", Icons.Airplane) { Destinations.Icons.toRoute() },
        MenuItem("Illustrations", Icons.Gallery) { Destinations.Illustrations.toRoute() },
        MenuItem("Typography", MIcons.FormatSize) { Destinations.Typography.toRoute() },
    )
    val controls = listOf(
        MenuItem("Alert", Icons.Alert) { Destinations.Alert.toRoute() },
        MenuItem("Badge", Icons.Deals) { Destinations.Badge.toRoute() },
        MenuItem("Button", MIcons.SmartButton) { Destinations.Button.toRoute() },
        MenuItem("Card / Tile", MIcons.Article) { Destinations.Card.toRoute() },
        MenuItem("Checkbox", MIcons.CheckBox) { Destinations.Checkbox.toRoute() },
        MenuItem("Choice Tile", MIcons.Ballot) { Destinations.ChoiceTile.toRoute() },
        MenuItem("Dialog", Icons.Chat) { Destinations.Dialog.toRoute() },
        MenuItem("EmptyState", MIcons.SignalWifiOff) { Destinations.EmptyState.toRoute() },
        MenuItem("KeyValue", MIcons.DragHandle) { Destinations.KeyValue.toRoute() },
        MenuItem("List", MIcons.DensitySmall) { Destinations.List.toRoute() },
        MenuItem("ListChoice", Icons.MenuHamburger) { Destinations.ListChoice.toRoute() },
        MenuItem("Loading", Icons.MenuMeatballs) { Destinations.Loading.toRoute() },
        MenuItem("PillButton", MIcons.EditAttributes) { Destinations.PillButton.toRoute() },
        MenuItem("Progress Indicator", OMIcons.ToggleOff) { Destinations.LinearProgressIndicator.toRoute() },
        MenuItem("Radio", Icons.CircleFilled) { Destinations.Radio.toRoute() },
        MenuItem("Seat", Icons.Seat) { Destinations.Seat.toRoute() },
        MenuItem("Segmented Switch", MIcons.ToggleOn) { Destinations.SegmentedSwitch.toRoute() },
        MenuItem("Select Field", MIcons.MenuOpen) { Destinations.SelectField.toRoute() },
        MenuItem("Stepper", Icons.PlusCircle) { Destinations.Stepper.toRoute() },
        MenuItem("Switch", MIcons.ToggleOn) { Destinations.Switch.toRoute() },
        MenuItem("Tag", MIcons.LabelImportant) { Destinations.Tag.toRoute() },
        MenuItem("Text Field", MIcons.Keyboard) { Destinations.TextField.toRoute() },
        MenuItem("Toast", MIcons.Announcement) { Destinations.Toast.toRoute() },
        MenuItem("TopAppBar", MIcons.WebAsset) { Destinations.TopAppBar.toRoute() },
    )

    Scaffold(
        topBar = {
            TopAppBarLarge(
                title = { Text("Orbit Compose Catalog") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(MIcons.BrightnessMedium, contentDescription = null)
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
    items: List<MenuItem>,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Text(
            text = title,
            style = OrbitTheme.typography.title3,
            modifier = Modifier.padding(vertical = 4.dp),
        )
    }
    items(items) { item -> Item(item) }
}

@Composable
private fun Item(menuItem: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = menuItem.onClick,
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                when (val icon = menuItem.icon) {
                    is Painter -> Icon(icon, contentDescription = null)
                    is ImageVector -> Icon(icon, contentDescription = null)
                    else -> {}
                }
                Spacer(Modifier.size(8.dp))
                Text(menuItem.title)
            }
        }
    }
}
