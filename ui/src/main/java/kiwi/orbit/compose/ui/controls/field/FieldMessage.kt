package kiwi.orbit.compose.ui.controls.field

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun FieldMessage(
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
) {
    val state = when {
        error != null -> Message.Error(error)
        info != null -> Message.Info(info)
        else -> null
    }
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            if (targetState == null || initialState == null) {
                val enter = slideInVertically(animationSpec = tween(AnimationDuration)) +
                    fadeIn(animationSpec = tween(AnimationDuration))
                val exit = slideOutVertically(animationSpec = tween(AnimationDuration)) +
                    fadeOut(animationSpec = tween(AnimationDuration))
                val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                enter with exit using size
            } else {
                val enter = fadeIn(animationSpec = tween(AnimationDuration))
                val exit = fadeOut(animationSpec = tween(AnimationDuration))
                val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                enter with exit using size
            }
        },
    ) { message ->
        if (message != null) {
            Row(Modifier.padding(top = 6.dp)) {
                val icon = when (message) {
                    is Message.Error -> Icons.AlertCircle
                    is Message.Info -> Icons.InformationCircle
                }
                val tintColor = when (message) {
                    is Message.Error -> OrbitTheme.colors.critical.main
                    is Message.Info -> OrbitTheme.colors.interactive.main
                }
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 2.dp, end = 4.dp)
                        .size(16.dp),
                    tint = tintColor,
                )
                ProvideMergedTextStyle(LocalTextStyle.current.copy(color = tintColor)) {
                    message.content.invoke()
                }
            }
        }
    }
}

private sealed class Message(
    open val content: @Composable (() -> Unit)
) {
    data class Error(override val content: @Composable () -> Unit) : Message(content)
    data class Info(override val content: @Composable () -> Unit) : Message(content)
}

private const val AnimationDuration = 150
