package kiwi.orbit.compose.ui.controls.field

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.layout.size

@Composable
internal fun FieldMessage(
    showInfo: Boolean,
    onErrorResolved: (Boolean) -> Unit,
    error: @Composable () -> Unit,
    info: @Composable () -> Unit,
) {
    val showError = remember { mutableStateOf(false) }

    Layout(
        contents = listOf(
            error,
            {
                Log.d("HRACH", "Before FieldErrorInfoMessage")
                FieldErrorInfoMessage(
                    showError = showError.value,
                    showInfo = showInfo,
                    error = error,
                    info = info,
                )
            },
        ),
    ) { (errorMeasurables, errorInfoMeasurable), constraints ->
        Log.d("HRACH", "Layout 0")
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val errorPlaceables = errorMeasurables.map { it.measure(looseConstraints) }
        val errorWidth = errorPlaceables.maxOfOrNull { it.width } ?: 0
        val errorHeight = errorPlaceables.maxOfOrNull { it.height } ?: 0

        showError.value = errorWidth > 0 && errorHeight > 0
        onErrorResolved(showError.value)

        Log.d("HRACH", "Layout 1 ${showError.value}")

        val errorInfoPlaceable = errorInfoMeasurable.first().measure(looseConstraints)

        Log.d("HRACH", "Layout 2")

        layout(errorInfoPlaceable.width, errorInfoPlaceable.height) {
            errorInfoPlaceable.place(0, 0)
        }
    }
}

@Composable
private fun FieldErrorInfoMessage(
    showError: Boolean,
    showInfo: Boolean,
    error: @Composable () -> Unit,
    info: @Composable () -> Unit,
) {
    val state = when {
        showError -> Message.Error(error)
        showInfo -> Message.Info(info)
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
            val icon = when (message) {
                is Message.Error -> Icons.AlertCircle
                is Message.Info -> Icons.InformationCircle
            }
            val tintColor = when (message) {
                is Message.Error -> OrbitTheme.colors.critical.normal
                is Message.Info -> OrbitTheme.colors.info.normal
            }
            FieldMessageIconText(
                color = tintColor,
                icon = icon,
                content = message.content,
            )
        } else {
            // Workaround to prevent shrinking in case of propagated min constraints to fill width.
            Box {}
        }
    }
}

@Composable
private fun FieldMessageIconText(
    color: Color,
    icon: Painter,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(height = 20.sp, width = 16.sp)
                    .wrapContentHeight()
                    .size(16.sp),
            )
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal.copy(color = color)) {
                content()
            }
        },
    ) { measurables, constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val iconPlaceable = measurables[0].measure(looseConstraints)
        val messagePlaceable = measurables.getOrNull(1)?.measure(
            looseConstraints.offset(horizontal = -iconPlaceable.width),
        )
        if (messagePlaceable == null || messagePlaceable.height == 0) {
            return@Layout layout(0, 0) {}
        }
        val padding = 4.dp.roundToPx()
        val height = maxOf(iconPlaceable.height, messagePlaceable.height) + padding
        layout(constraints.minWidth, height) {
            iconPlaceable.placeRelative(0, padding)
            messagePlaceable.placeRelative(iconPlaceable.width, padding)
        }
    }
}

private sealed class Message(
    open val content: @Composable (() -> Unit),
) {
    data class Error(override val content: @Composable () -> Unit) : Message(content)
    data class Info(override val content: @Composable () -> Unit) : Message(content)
}

private const val AnimationDuration = 150
