package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs

@Stable
public interface TopAppBarScrollBehavior {

    /**
     * A [NestedScrollConnection] that should be attached to a [Modifier.nestedScroll] in order to
     * keep track of the scroll events.
     */
    public val nestedScrollConnection: NestedScrollConnection

    /**
     * Returns the top app bar's current scroll fraction.
     *
     * A scrollFraction is a value between `0.0` to `1.0` that provides a percentage of the app
     * bar scroll position when the content is scrolled. `0.0` represents an expanded app bar,
     * while `1.0`
     * represents a collapsed one (e.g. the app bar is scrolled to its target offset). Note that
     * this value will be updated on scroll even if the [offset] is pinned to a specific
     * value (see [TopAppBarDefaults.pinnedScrollBehavior]). In this case a value of 1.0 represents
     * that the scroll value has exceeded the height of the pinned app bar, as if the app bar was
     * collapsing.
     */
    public val scrollFraction: Float

    /**
     * The top app bar's offset limit in pixels, which represents the offset that a top app bar is
     * allowed to scroll when the scrollable content is scrolled.
     *
     * This limit is represented by a negative [Float], and used to coerce the [offset] value when
     * the content is scrolled.
     */
    public var offsetLimit: Float

    /**
     * The top app bar's current offset in pixels.
     *
     * The offset is usually between zero and the [offsetLimit].
     */
    public var offset: Float

    /**
     * The current content offset that is updated when the nested scroll connection consumes scroll
     * events.
     *
     * A common behavior implementation would update this value to be the sum of all
     * [NestedScrollConnection.onPostScroll] `consumed.y` values.
     */
    public var contentOffset: Float

    public companion object {
        public fun pinnedScrollBehavior(
            canScroll: () -> Boolean = { true }
        ): TopAppBarScrollBehavior =
            PinnedScrollBehavior(canScroll)

        public fun enterAlwaysScrollBehavior(
            canScroll: () -> Boolean = { true }
        ): TopAppBarScrollBehavior =
            EnterAlwaysScrollBehavior(canScroll)

        public fun exitUntilCollapsedScrollBehavior(
            decayAnimationSpec: DecayAnimationSpec<Float>,
            canScroll: () -> Boolean = { true },
        ): TopAppBarScrollBehavior =
            ExitUntilCollapsedScrollBehavior(decayAnimationSpec, canScroll)
    }
}

/**
 * Returns a [TopAppBarScrollBehavior] that only adjusts its content offset, without adjusting any
 * properties that affect the height of a top app bar.
 *
 * @param canScroll a callback used to determine whether scroll events are to be
 * handled by this [PinnedScrollBehavior]
 */
private class PinnedScrollBehavior(val canScroll: () -> Boolean = { true }) :
    TopAppBarScrollBehavior {
    override var offsetLimit = -Float.MAX_VALUE
    override val scrollFraction: Float
        get() = if (offsetLimit != 0f) {
            1 - (
                (offsetLimit - contentOffset).coerceIn(
                    minimumValue = offsetLimit,
                    maximumValue = 0f
                ) / offsetLimit
                )
        } else {
            0f
        }
    override var offset = 0f
    override var contentOffset by mutableStateOf(0f)
    override var nestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canScroll()) return Offset.Zero
                if (consumed.y == 0f && available.y > 0f) {
                    // Reset the total offset to zero when scrolling all the way down. This will
                    // eliminate some float precision inaccuracies.
                    contentOffset = 0f
                } else {
                    contentOffset += consumed.y
                }
                return Offset.Zero
            }
        }
}

/**
 * A [TopAppBarScrollBehavior] that adjusts its properties to affect the colors and height of a top
 * app bar.
 *
 * A top app bar that is set up with this [TopAppBarScrollBehavior] will immediately collapse when
 * the nested content is pulled up, and will immediately appear when the content is pulled down.
 *
 * @param canScroll a callback used to determine whether scroll events are to be
 * handled by this [EnterAlwaysScrollBehavior]
 */
private class EnterAlwaysScrollBehavior(val canScroll: () -> Boolean = { true }) :
    TopAppBarScrollBehavior {
    override val scrollFraction: Float
        get() = if (offsetLimit != 0f) {
            1 - (
                (offsetLimit - contentOffset).coerceIn(
                    minimumValue = offsetLimit,
                    maximumValue = 0f
                ) / offsetLimit
                )
        } else {
            0f
        }
    override var offsetLimit by mutableStateOf(-Float.MAX_VALUE)
    override var offset by mutableStateOf(0f)
    override var contentOffset by mutableStateOf(0f)
    override var nestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (!canScroll()) return Offset.Zero
                val newOffset = (offset + available.y)
                val coerced = newOffset.coerceIn(minimumValue = offsetLimit, maximumValue = 0f)
                return if (newOffset == coerced) {
                    // Nothing coerced, meaning we're in the middle of top app bar collapse or
                    // expand.
                    offset = coerced
                    // Consume only the scroll on the Y axis.
                    available.copy(x = 0f)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canScroll()) return Offset.Zero
                contentOffset += consumed.y
                if (offset == 0f || offset == offsetLimit) {
                    if (consumed.y == 0f && available.y > 0f) {
                        // Reset the total offset to zero when scrolling all the way down.
                        // This will eliminate some float precision inaccuracies.
                        contentOffset = 0f
                    }
                }
                offset = (offset + consumed.y).coerceIn(
                    minimumValue = offsetLimit,
                    maximumValue = 0f
                )
                return Offset.Zero
            }
        }
}

/**
 * A [TopAppBarScrollBehavior] that adjusts its properties to affect the colors and height of a top
 * app bar.
 *
 * A top app bar that is set up with this [TopAppBarScrollBehavior] will immediately collapse when
 * the nested content is pulled up, and will expand back the collapsed area when the content is
 * pulled all the way down.
 *
 * @param decayAnimationSpec a [DecayAnimationSpec] that will be used by the top app bar motion
 * when the user flings the content. Preferably, this should match the animation spec used by the
 * scrollable content. See also [androidx.compose.animation.rememberSplineBasedDecay] for a
 * default [DecayAnimationSpec] that can be used with this behavior.
 * @param canScroll a callback used to determine whether scroll events are to be
 * handled by this [ExitUntilCollapsedScrollBehavior]
 */
private class ExitUntilCollapsedScrollBehavior(
    val decayAnimationSpec: DecayAnimationSpec<Float>,
    val canScroll: () -> Boolean = { true }
) : TopAppBarScrollBehavior {
    override val scrollFraction: Float
        get() = if (offsetLimit != 0f) offset / offsetLimit else 0f
    override var offsetLimit by mutableStateOf(-Float.MAX_VALUE)
    override var offset by mutableStateOf(0f)
    override var contentOffset by mutableStateOf(0f)
    override var nestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Don't intercept if scrolling down.
                if (!canScroll() || available.y > 0f) return Offset.Zero

                val newOffset = (offset + available.y)
                val coerced = newOffset.coerceIn(minimumValue = offsetLimit, maximumValue = 0f)
                return if (newOffset == coerced) {
                    // Nothing coerced, meaning we're in the middle of top app bar collapse or
                    // expand.
                    offset = coerced
                    // Consume only the scroll on the Y axis.
                    available.copy(x = 0f)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canScroll()) return Offset.Zero
                contentOffset += consumed.y

                if (available.y < 0f || consumed.y < 0f) {
                    // When scrolling up, just update the state's offset.
                    val oldOffset = offset
                    offset = (offset + consumed.y).coerceIn(
                        minimumValue = offsetLimit,
                        maximumValue = 0f
                    )
                    return Offset(0f, offset - oldOffset)
                }

                if (consumed.y == 0f && available.y > 0) {
                    // Reset the total offset to zero when scrolling all the way down. This will
                    // eliminate some float precision inaccuracies.
                    contentOffset = 0f
                }

                if (available.y > 0f) {
                    // Adjust the offset in case the consumed delta Y is less than what was recorded
                    // as available delta Y in the pre-scroll.
                    val oldOffset = offset
                    offset = (offset + available.y).coerceIn(
                        minimumValue = offsetLimit,
                        maximumValue = 0f
                    )
                    return Offset(0f, offset - oldOffset)
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val result = super.onPostFling(consumed, available)
                // TODO(b/179417109): We get positive Velocity when flinging up while the top app
                //  bar is changing its height. Track b/179417109 for a fix.
                if ((available.y < 0f && contentOffset == 0f) ||
                    (available.y > 0f && offset < 0f)
                ) {
                    return result +
                        onTopBarFling(
                            scrollBehavior = this@ExitUntilCollapsedScrollBehavior,
                            initialVelocity = available.y,
                            decayAnimationSpec = decayAnimationSpec,
                            snap = true
                        )
                }
                return result
            }
        }
}

private suspend fun onTopBarFling(
    scrollBehavior: TopAppBarScrollBehavior,
    initialVelocity: Float,
    decayAnimationSpec: DecayAnimationSpec<Float>,
    snap: Boolean
): Velocity {
    if (abs(initialVelocity) > 1f) {
        var remainingVelocity = initialVelocity
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = initialVelocity,
        )
            .animateDecay(decayAnimationSpec) {
                val delta = value - lastValue
                val initialOffset = scrollBehavior.offset
                scrollBehavior.offset =
                    (initialOffset + delta).coerceIn(
                        minimumValue = scrollBehavior.offsetLimit,
                        maximumValue = 0f
                    )
                val consumed = abs(initialOffset - scrollBehavior.offset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }

        if (snap &&
            scrollBehavior.offset < 0 &&
            scrollBehavior.offset > scrollBehavior.offsetLimit
        ) {
            AnimationState(initialValue = scrollBehavior.offset).animateTo(
                // Snap the top app bar offset to completely collapse or completely expand according
                // to the initial velocity direction.
                if (initialVelocity > 0) 0f else scrollBehavior.offsetLimit,
                animationSpec = tween(
                    durationMillis = TopAppBarAnimationDurationMillis,
                    easing = LinearOutSlowInEasing
                )
            ) { scrollBehavior.offset = value }
        }
        return Velocity(0f, remainingVelocity)
    }
    return Velocity.Zero
}

private const val TopAppBarAnimationDurationMillis = 500
