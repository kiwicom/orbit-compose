package kiwi.orbit.compose.catalog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.ToastHostState
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun Screen(
    title: String,
    onNavigateUp: () -> Unit,
    action: @Composable (() -> Unit)? = null,
    toastHostState: ToastHostState = remember { ToastHostState() },
    topAppBarElevation: Dp = 2.dp,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                onNavigateUp = onNavigateUp,
                elevation = topAppBarElevation,
            )
        },
        action = action,
        toastHostState = toastHostState,
        content = content,
    )
}
