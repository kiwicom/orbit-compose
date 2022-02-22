package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.layout.expand

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun <S> ButtonToggleContainer(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform = {
        fadeIn(tween(AnimationDuration)) with fadeOut(tween(AnimationDuration))
    },
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    AnimatedContent(
        modifier = modifier.expand(16.dp),
        targetState = targetState,
        transitionSpec = transitionSpec,
        contentAlignment = contentAlignment,
    ) { state ->
        Box(Modifier.padding(16.dp)) {
            content(state)
        }
    }
}

private const val AnimationDuration = 300
