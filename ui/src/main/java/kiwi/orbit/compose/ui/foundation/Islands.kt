@file:Suppress("Dependency")

package kiwi.orbit.compose.ui.foundation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import kiwi.orbit.compose.ui.OrbitRippleTheme

/**
 * UI island providing Material theming.
 *
 * By design, Material theming is enabled along with the [kiwi.orbit.compose.ui.OrbitTheme].
 * However, some UI concepts has to decide if they follow Material or Orbit current state.
 * For now this is about Ripple coloring that has to decide if follow Material or Orbit current
 * text color. By using [MaterialIsland] you switch this handling to match the Material.
 * Use [OrbitIsland] to revert it back.
 *
 * ```
 * MaterialIsland {
 *   androidx.compose.material.TextButton(onClick = {}) {
 *     androidx.compose.material.Text("Ok")
 *   }
 * }
 * ```
 */
@Composable
public fun MaterialIsland(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRippleTheme provides MaterialRippleTheme,
        content = content,
    )
}

/**
 * UI island providing Orbit theming.
 *
 * Reverts theming back to orbit. Read more in [MaterialIsland] doc.
 */
@Composable
public fun OrbitIsland(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRippleTheme provides OrbitRippleTheme,
        content = content,
    )
}

@Immutable
private object MaterialRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = androidx.compose.material.LocalContentColor.current,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = androidx.compose.material.LocalContentColor.current,
        lightTheme = MaterialTheme.colors.isLight
    )
}
