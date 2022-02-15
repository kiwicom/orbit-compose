package kiwi.orbit.compose.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

public operator fun PaddingValues.plus(other: PaddingValues): PaddingValues =
    SummedPaddingValues(this, other)

private class SummedPaddingValues(
    private val a: PaddingValues,
    private val b: PaddingValues
) : PaddingValues {
    override fun calculateBottomPadding(): Dp {
        return a.calculateBottomPadding() + b.calculateBottomPadding()
    }

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return a.calculateLeftPadding(layoutDirection) + b.calculateLeftPadding(layoutDirection)
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return a.calculateRightPadding(layoutDirection) + b.calculateRightPadding(layoutDirection)
    }

    override fun calculateTopPadding(): Dp {
        return a.calculateTopPadding() + b.calculateTopPadding()
    }
}
