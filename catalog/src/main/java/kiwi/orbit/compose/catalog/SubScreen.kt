package kiwi.orbit.compose.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.catalog.components.Scaffold
import kiwi.orbit.compose.catalog.components.TopAppBar
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun SubScreen(
    title: String,
    onUpClick: () -> Unit,
    withBackground: Boolean = true,
    scrollable: Boolean = true,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.statusBars,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onUpClick,
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        content = {
            if (scrollable) {
                LazyColumn(
                    modifier = if (withBackground) Modifier
                        .background(OrbitTheme.colors.surface.main)
                        .fillMaxHeight()
                    else Modifier,
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.navigationBars
                    )
                ) {
                    item { content() }
                }
            } else {
                Box(
                    modifier = if (withBackground) Modifier
                        .background(OrbitTheme.colors.surface.main)
                        .fillMaxHeight()
                    else Modifier,
                ) {
                    content()
                }
            }
        },
    )
}
