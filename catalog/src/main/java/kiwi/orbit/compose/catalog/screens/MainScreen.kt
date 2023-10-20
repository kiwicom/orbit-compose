package kiwi.orbit.compose.catalog.screens

import androidx.activity.compose.ReportDrawn
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
import androidx.compose.material.icons.automirrored.rounded.Announcement
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.automirrored.rounded.LabelImportant
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.automirrored.rounded.MenuOpen
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material.icons.rounded.Ballot
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.Crop169
import androidx.compose.material.icons.rounded.DensitySmall
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.EditAttributes
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Rectangle
import androidx.compose.material.icons.rounded.SignalWifiOff
import androidx.compose.material.icons.rounded.SmartButton
import androidx.compose.material.icons.rounded.Tab
import androidx.compose.material.icons.rounded.ToggleOn
import androidx.compose.material.icons.rounded.WebAsset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.kiwi.navigationcompose.typed.Destination
import kiwi.orbit.compose.catalog.Destinations
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.SurfaceCard
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBarLarge
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.utils.plus
import kotlinx.serialization.ExperimentalSerializationApi
import androidx.compose.material.icons.Icons.AutoMirrored.Rounded as AMMIcons
import androidx.compose.material.icons.Icons.Outlined as OMIcons
import androidx.compose.material.icons.Icons.Rounded as MIcons
import kiwi.orbit.compose.catalog.semantics.MainScreenSemantics as Semantics

private data class MenuItem(
    val title: String,
    val icon: Any,
    val testTag: String,
    val onClick: () -> Unit,
)

@ExperimentalSerializationApi
@Composable
internal fun MainScreen(
    onNavigate: (Destination) -> Unit,
    onThemeToggle: (Offset) -> Unit,
) {
    fun MenuItem(title: String, icon: Any, testTag: String, onNavigate: () -> Destination): MenuItem =
        MenuItem(title, icon, testTag, onClick = { onNavigate(onNavigate()) })

    val foundation = listOf(
        MenuItem("Colors", MIcons.Palette, Semantics.ColorsItemTag) { Destinations.Colors },
        MenuItem("Icons", Icons.Airplane, Semantics.IconsItemTag) { Destinations.Icons },
        MenuItem("Illustrations", Icons.Gallery, Semantics.IllustrationsItemTag) {
            Destinations.Illustrations
        },
        MenuItem("Typography", MIcons.FormatSize, Semantics.TypographyItemTag) { Destinations.Typography },
    )
    val controls = listOf(
        MenuItem("Alert", Icons.Alert, Semantics.AlertItemTag) { Destinations.Alert },
        MenuItem("Badge", Icons.Deals, Semantics.BadgeItemTag) { Destinations.Badge },
        MenuItem("BadgeList", Icons.List, Semantics.BadgeListItemTag) { Destinations.BadgeList },
        MenuItem("Button", MIcons.SmartButton, Semantics.ButtonItemTag) { Destinations.Button },
        MenuItem("Card", MIcons.Rectangle, Semantics.CardItemTag) { Destinations.Card },
        MenuItem("Checkbox", MIcons.CheckBox, Semantics.CheckboxItemTag) { Destinations.Checkbox },
        MenuItem("Choice Tile", MIcons.Ballot, Semantics.ChoiceTileItemTag) { Destinations.ChoiceTile },
        MenuItem("Collapse", Icons.ChevronDown, Semantics.CollapseItemTag) { Destinations.Collapse },
        MenuItem("Coupon", Icons.PromoCode, Semantics.CouponItemTag) { Destinations.Coupon },
        MenuItem("Dialog", Icons.Chat, Semantics.DialogItemTag) { Destinations.Dialog },
        MenuItem("EmptyState", MIcons.SignalWifiOff, Semantics.EmptyStateItemTag) { Destinations.EmptyState },
        MenuItem("KeyValue", MIcons.DragHandle, Semantics.KeyValueItemTag) { Destinations.KeyValue },
        MenuItem("List", MIcons.DensitySmall, Semantics.ListItemTag) { Destinations.List },
        MenuItem("ListChoice", Icons.MenuHamburger, Semantics.ListChoiceItemTag) { Destinations.ListChoice },
        MenuItem("Loading", Icons.MenuMeatballs, Semantics.LoadingItemTag) { Destinations.Loading },
        MenuItem("PillButton", MIcons.EditAttributes, Semantics.PillButtonItemTag) {
            Destinations.PillButton
        },
        MenuItem("Progress Indicator", OMIcons.ToggleOff, Semantics.ProgressIndicatorItemTag) {
            Destinations.LinearProgressIndicator
        },
        MenuItem("Radio", Icons.CircleFilled, Semantics.RadioItemTag) { Destinations.Radio },
        MenuItem("Seat", Icons.Seat, Semantics.SeatItemTag) { Destinations.Seat },
        MenuItem("Segmented Switch", MIcons.ToggleOn, Semantics.SegmentedSwitchItemTag) {
            Destinations.SegmentedSwitch
        },
        MenuItem("Select Field", AMMIcons.MenuOpen, Semantics.SelectFieldItemTag) { Destinations.SelectField },
        MenuItem("Slider", MIcons.LinearScale, Semantics.SliderItemTag) { Destinations.Slider },
        MenuItem("Stepper", Icons.PlusCircle, Semantics.StepperItemTag) { Destinations.Stepper },
        MenuItem("SurfaceCard", AMMIcons.Article, Semantics.SurfaceCardItemTag) { Destinations.SurfaceCard },
        MenuItem("Switch", MIcons.ToggleOn, Semantics.SwitchItemTag) { Destinations.Switch },
        MenuItem("Tabs", MIcons.Tab, Semantics.TabsItemTag) { Destinations.Tabs },
        MenuItem("Tag", AMMIcons.LabelImportant, Semantics.TagItemTag) { Destinations.Tag },
        MenuItem("Text Field", MIcons.Keyboard, Semantics.TextFieldItemTag) { Destinations.TextField },
        MenuItem("Tile", MIcons.Crop169, Semantics.TileItemTag) { Destinations.Tile },
        MenuItem("TileGroup", AMMIcons.ListAlt, Semantics.TileGroupItemTag) { Destinations.TileGroup },
        MenuItem("Timeline", Icons.RouteTwoStops, Semantics.TimelineItemTag) { Destinations.Timeline },
        MenuItem("Toast", AMMIcons.Announcement, Semantics.ToastItemTag) { Destinations.Toast },
        MenuItem("TopAppBar", MIcons.WebAsset, Semantics.TopAppBarItemTag) { Destinations.TopAppBar },
    )

    Scaffold(
        modifier = Modifier.testTag(Semantics.Tag),
        topBar = {
            TopAppBarLarge(
                title = { Text("Orbit Compose Catalog") },
                actions = {
                    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
                    IconButton(
                        modifier = Modifier.onGloballyPositioned {
                            offset = it.positionInWindow() + it.size.center.toOffset()
                        },
                        onClick = { onThemeToggle(offset) },
                    ) {
                        Icon(MIcons.BrightnessMedium, contentDescription = null)
                    }
                },
            )
        },
        backgroundColor = OrbitTheme.colors.surface.subtle,
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = contentPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            cardItems("Foundation", foundation)
            cardItems("Controls", controls)
        }
    }

    ReportDrawn()
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
    SurfaceCard(
        modifier = Modifier
            .testTag(menuItem.testTag)
            .fillMaxWidth(),
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
                Text(menuItem.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
