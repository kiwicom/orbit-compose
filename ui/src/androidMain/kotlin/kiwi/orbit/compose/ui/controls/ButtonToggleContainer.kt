package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.layout.expand

@Composable
public fun <S> ButtonToggleContainer(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform = {
        fadeIn(tween(AnimationDuration)) togetherWith fadeOut(tween(AnimationDuration))
    },
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit,
) {
    AnimatedContent(
        modifier = modifier.expand(16.dp),
        targetState = targetState,
        transitionSpec = transitionSpec,
        contentAlignment = contentAlignment,
        label = "ButtonToggleContainer",
    ) { state ->
        Box(Modifier.padding(16.dp)) {
            content(state)
        }
    }
}

private const val AnimationDuration = 300
