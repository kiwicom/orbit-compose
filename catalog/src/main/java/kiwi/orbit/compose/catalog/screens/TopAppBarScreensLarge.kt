package kiwi.orbit.compose.catalog.screens

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transition.CrossfadeTransition
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBarLarge
import kiwi.orbit.compose.ui.controls.TopAppBarScrollBehavior
import kiwi.orbit.compose.ui.foundation.ContentEmphasis

@Composable
internal fun TopAppBarLargeScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBarLarge(
                title = { Text("Simple") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = Icons.Notification, contentDescription = null)
                    }
                }
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableScreen(
    onNavigateUp: () -> Unit,
) {
    val decay = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember { TopAppBarScrollBehavior.exitUntilCollapsedScrollBehavior(decay) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = false,
            )
        },
        backgroundColor = OrbitTheme.colors.surface.background,
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableElevatedScreen(
    onNavigateUp: () -> Unit,
) {
    val decay = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember { TopAppBarScrollBehavior.exitUntilCollapsedScrollBehavior(decay) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable Elevated") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = true,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun TopAppBarLargeCustomContentScreen(
    onNavigateUp: () -> Unit,
) {
    val decay = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember { TopAppBarScrollBehavior.exitUntilCollapsedScrollBehavior(decay) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Jonathan Archer")
                            Text(
                                text = "j.archer@nx01.starfleet",
                                style = OrbitTheme.typography.bodySmall,
                                emphasis = ContentEmphasis.Minor
                            )
                        }
                        Image(
                            painter = rememberImagePainter(ArcherPhoto) {
                                transition(CrossfadeTransition())
                                transformations(CircleCropTransformation())
                                size(OriginalSize)
                                scale(Scale.FIT)
                            },
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                largeTitle = {
                    Column {
                        Row {
                            Text("Jonathan Archer", Modifier.weight(1f))
                            Image(
                                painter = rememberImagePainter(ArcherPhoto) {
                                    transition(CrossfadeTransition())
                                    transformations(CircleCropTransformation())
                                    size(OriginalSize)
                                    scale(Scale.FIT)
                                },
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Text(
                            text = "j.archer@nx01.starfleet",
                            style = OrbitTheme.typography.bodyNormal,
                            emphasis = ContentEmphasis.Minor
                        )
                    }
                },
                onNavigateUp = onNavigateUp,
                largeElevated = false,
                scrollBehavior = scrollBehavior,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

private const val ArcherPhoto = "https://upload.wikimedia.org/wikipedia/commons/2/23/J_Archer.jpg"
