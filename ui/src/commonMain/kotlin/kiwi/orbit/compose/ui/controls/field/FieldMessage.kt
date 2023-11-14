package kiwi.orbit.compose.ui.controls.field

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.layout.size

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
                enter togetherWith exit using size
            } else {
                val enter = fadeIn(animationSpec = tween(AnimationDuration))
                val exit = fadeOut(animationSpec = tween(AnimationDuration))
                val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                enter togetherWith exit using size
            }
        },
        label = "FieldErrorInfoMessage",
    ) { message ->
        if (message != null) {
            Row(Modifier.padding(top = 4.dp)) {
                val icon = when (message) {
                    is Message.Error -> Icons.AlertCircle
                    is Message.Info -> Icons.InformationCircle
                }
                val tintColor = when (message) {
                    is Message.Error -> OrbitTheme.colors.critical.normal
                    is Message.Info -> OrbitTheme.colors.info.normal
                }
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(height = 20.sp, width = 16.sp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.sp),
                        tint = tintColor,
                    )
                }
                ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal.copy(color = tintColor)) {
                    message.content.invoke()
                }
            }
        } else {
            // Workaround to prevent shrinking in case of propagated min constraints to fill width.
            Box {}
        }
    }
}

private sealed class Message(
    open val content: @Composable (() -> Unit),
) {
    data class Error(
        override val content: @Composable () -> Unit,
    ) : Message(content)

    data class Info(
        override val content: @Composable () -> Unit,
    ) : Message(content)
}

private const val AnimationDuration = 150
