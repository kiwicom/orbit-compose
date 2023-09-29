package kiwi.orbit.compose.catalog

import com.kiwi.navigationcompose.typed.Destination
import com.kiwi.navigationcompose.typed.ResultDestination
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

sealed interface Destinations : Destination {
    @Serializable
    data object Main : Destinations

    @Serializable
    data object Colors : Destinations

    @Serializable
    data object Icons : Destinations

    @Serializable
    data object Illustrations : Destinations

    @Serializable
    data object Typography : Destinations

    @Serializable
    data object Alert : Destinations

    @Serializable
    data object Badge : Destinations

    @Serializable
    data object BadgeList : Destinations

    @Serializable
    data object Button : Destinations

    @Serializable
    data object Card : Destinations

    @Serializable
    data object Checkbox : Destinations

    @Serializable
    data object ChoiceTile : Destinations

    @Serializable
    data object Collapse : Destinations

    @Serializable
    data object Coupon : Destinations

    @Serializable
    data object Dialog : Destinations

    @Serializable
    data object DialogMaterial : Destinations

    @Serializable
    data object DialogMaterialTimePicker : Destinations, ResultDestination<DialogMaterialTimePicker.Result> {
        @Serializable
        data class Result(val time: LocalTime)
    }

    @Serializable
    data object DialogMaterialDatePicker : Destinations, ResultDestination<DialogMaterialDatePicker.Result> {
        @Serializable
        data class Result(val date: LocalDate)
    }

    @Serializable
    data object DialogOrbit : Destinations

    @Serializable
    data object EmptyState : Destinations

    @Serializable
    data object KeyValue : Destinations

    @Serializable
    data object LinearProgressIndicator : Destination

    @Serializable
    data object List : Destinations

    @Serializable
    data object ListChoice : Destinations

    @Serializable
    data object Loading : Destinations

    @Serializable
    data object Radio : Destinations

    @Serializable
    data object PillButton : Destinations

    @Serializable
    data object Seat : Destinations

    @Serializable
    data object SegmentedSwitch : Destinations

    @Serializable
    data object SelectField : Destinations

    @Serializable
    data object Slider : Destinations

    @Serializable
    data object Stepper : Destinations

    @Serializable
    data object SurfaceCard : Destinations

    @Serializable
    data object Switch : Destinations

    @Serializable
    data object Tabs : Destinations

    @Serializable
    data object Tag : Destinations

    @Serializable
    data object TextField : Destinations

    @Serializable
    data object Tile : Destinations

    @Serializable
    data object TileGroup : Destinations

    @Serializable
    data object Timeline : Destinations

    @Serializable
    data object Toast : Destinations

    @Serializable
    data object TopAppBar : Destinations
}
