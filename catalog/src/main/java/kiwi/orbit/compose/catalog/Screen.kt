package kiwi.orbit.compose.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.catalog.components.Scaffold
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun Screen(
    title: String,
    onUpClick: () -> Unit,
    withBackground: Boolean = true,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                onBack = onUpClick,
            )
        },
        content = {
            val contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.navigationBars
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        if (withBackground) {
                            OrbitTheme.colors.surface.main
                        } else {
                            OrbitTheme.colors.surface.background
                        }
                    )
            ) {
                content(contentPadding)
            }
        },
    )
}
