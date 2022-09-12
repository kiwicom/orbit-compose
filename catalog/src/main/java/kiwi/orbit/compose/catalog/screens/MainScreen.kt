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
import com.kiwi.navigationcompose.typed.Destination
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
    onNavigate: (Destination) -> Unit,
    onToggleTheme: () -> Unit,
) {
    fun MenuItem(title: String, icon: Any, onNavigate: () -> Destination): MenuItem =
        MenuItem(title, icon, onClick = { onNavigate(onNavigate()) })

    val foundation = listOf(
        MenuItem("Colors", MIcons.Palette) { Destinations.Colors },
        MenuItem("Icons", Icons.Airplane) { Destinations.Icons },
        MenuItem("Illustrations", Icons.Gallery) { Destinations.Illustrations },
        MenuItem("Typography", MIcons.FormatSize) { Destinations.Typography },
    )
    val controls = listOf(
        MenuItem("Alert", Icons.Alert) { Destinations.Alert },
        MenuItem("Badge", Icons.Deals) { Destinations.Badge },
        MenuItem("Button", MIcons.SmartButton) { Destinations.Button },
        MenuItem("Card / Tile", MIcons.Article) { Destinations.Card },
        MenuItem("Checkbox", MIcons.CheckBox) { Destinations.Checkbox },
        MenuItem("Choice Tile", MIcons.Ballot) { Destinations.ChoiceTile },
        MenuItem("Dialog", Icons.Chat) { Destinations.Dialog },
        MenuItem("EmptyState", MIcons.SignalWifiOff) { Destinations.EmptyState },
        MenuItem("KeyValue", MIcons.DragHandle) { Destinations.KeyValue },
        MenuItem("List", MIcons.DensitySmall) { Destinations.List },
        MenuItem("ListChoice", Icons.MenuHamburger) { Destinations.ListChoice },
        MenuItem("Loading", Icons.MenuMeatballs) { Destinations.Loading },
        MenuItem("PillButton", MIcons.EditAttributes) { Destinations.PillButton },
        MenuItem("Progress Indicator", OMIcons.ToggleOff) { Destinations.LinearProgressIndicator },
        MenuItem("Radio", Icons.CircleFilled) { Destinations.Radio },
        MenuItem("Seat", Icons.Seat) { Destinations.Seat },
        MenuItem("Segmented Switch", MIcons.ToggleOn) { Destinations.SegmentedSwitch },
        MenuItem("Select Field", MIcons.MenuOpen) { Destinations.SelectField },
        MenuItem("Stepper", Icons.PlusCircle) { Destinations.Stepper },
        MenuItem("Switch", MIcons.ToggleOn) { Destinations.Switch },
        MenuItem("Tag", MIcons.LabelImportant) { Destinations.Tag },
        MenuItem("Text Field", MIcons.Keyboard) { Destinations.TextField },
        MenuItem("Toast", MIcons.Announcement) { Destinations.Toast },
        MenuItem("TopAppBar", MIcons.WebAsset) { Destinations.TopAppBar },
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
