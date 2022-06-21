package kiwi.orbit.compose.ui.controls.internal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Stable
internal class MutablePaddingValues : PaddingValues {
    var start: Dp by mutableStateOf(0.dp)
    var top: Dp by mutableStateOf(0.dp)
    var end: Dp by mutableStateOf(0.dp)
    var bottom: Dp by mutableStateOf(0.dp)

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return when (layoutDirection) {
            LayoutDirection.Ltr -> start
            LayoutDirection.Rtl -> end
        }
    }

    override fun calculateTopPadding(): Dp = top

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return when (layoutDirection) {
            LayoutDirection.Ltr -> end
            LayoutDirection.Rtl -> start
        }
    }

    override fun calculateBottomPadding(): Dp = bottom

    fun updateFrom(top: Dp, start: Dp, end: Dp, bottom: Dp) {
        if (this.top != top) {
            this.top = top
        }
        if (this.start != start) {
            this.start = start
        }
        if (this.end != end) {
            this.end = end
        }
        if (this.bottom != bottom) {
            this.bottom = bottom
        }
    }
}
