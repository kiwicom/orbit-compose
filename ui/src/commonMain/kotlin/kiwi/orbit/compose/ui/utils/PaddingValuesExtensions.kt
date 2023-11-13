package kiwi.orbit.compose.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

public operator fun PaddingValues.plus(other: PaddingValues): PaddingValues =
    UnionPaddingValues(this, other)

private class UnionPaddingValues(
    private val a: PaddingValues,
    private val b: PaddingValues,
) : PaddingValues {
    override fun calculateBottomPadding(): Dp =
        a.calculateBottomPadding() + b.calculateBottomPadding()

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
        a.calculateLeftPadding(layoutDirection) + b.calculateLeftPadding(layoutDirection)

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
        a.calculateRightPadding(layoutDirection) + b.calculateRightPadding(layoutDirection)

    override fun calculateTopPadding(): Dp =
        a.calculateTopPadding() + b.calculateTopPadding()
}
