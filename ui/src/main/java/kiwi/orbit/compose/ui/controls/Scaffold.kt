package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.WindowInsets
import com.google.accompanist.insets.derivedWindowInsetsTypeOf
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.contentColorFor

public val LocalScaffoldPadding: ProvidableCompositionLocal<PaddingValues> =
    staticCompositionLocalOf { PaddingValues(0.dp) }

@Composable
public fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(backgroundColor),
    toastHostState: ToastHostState = remember { ToastHostState() },
    toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
    content: @Composable (contentPadding: PaddingValues) -> Unit
) {
    // Combines bars' and IME's offsets, uses the max of bottom bar'S and IME's insets.
    val ime = LocalWindowInsets.current.ime
    val navBars = LocalWindowInsets.current.navigationBars
    val insets = remember(ime, navBars) { derivedWindowInsetsTypeOf(ime, navBars) }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        ScaffoldLayout(
            topBar = topBar,
            toast = { toastHost(toastHostState) },
            content = content,
            insets = insets,
        )
    }
}

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    toast: @Composable () -> Unit,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
    insets: WindowInsets.Type,
) {
    val insetsPadding = rememberInsetsPaddingValues(insets)
    val contentPadding = remember { MutablePaddingValues() }

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val topBarPlaceables = subcompose(ScaffoldLayoutContent.TopBar, topBar).map {
                it.measure(looseConstraints)
            }

            val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height
            val mainConstraints = looseConstraints.copy(maxHeight = layoutHeight - (topBarHeight ?: 0))

            val toastPlaceables = subcompose(ScaffoldLayoutContent.Toast, toast).map {
                it.measure(mainConstraints)
            }

            val topInset = topBarHeight?.toDp() ?: insetsPadding.calculateTopPadding()
            contentPadding.apply {
                start = insetsPadding.calculateStartPadding(LayoutDirection.Ltr)
                top = topInset
                end = insetsPadding.calculateEndPadding(LayoutDirection.Ltr)
                bottom = insetsPadding.calculateBottomPadding()
            }

            val contentPlaceables = subcompose(ScaffoldLayoutContent.Content) {
                CompositionLocalProvider(LocalScaffoldPadding provides contentPadding) {
                    content(contentPadding)
                }
            }.map {
                it.measure(looseConstraints.copy(maxHeight = layoutHeight))
            }

            contentPlaceables.forEach { it.place(0, 0) }
            topBarPlaceables.forEach { it.place(0, 0) }
            toastPlaceables.forEach {
                // place it centered for tablet layouts
                it.place((layoutWidth - it.measuredWidth) / 2, topBarHeight ?: 0)
            }
        }
    }
}

private enum class ScaffoldLayoutContent {
    TopBar,
    Content,
    Toast,
}

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
}
