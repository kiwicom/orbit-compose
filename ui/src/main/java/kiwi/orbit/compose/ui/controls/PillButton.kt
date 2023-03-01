package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

/**
 * A host Composable for the [PillButton] Composable.
 *
 * Draws a [Box] container for [content]. Such [Box] can be modified via [modifier].
 *
 * ```
 * var visible by remember { mutableStateOf(true) }
 * PillButtonContainer(
 *     button = {
 *         PillButton(
 *             onClick = { visible = false },
 *             icon = {
 *                 Icon(
 *                     painter = Icons.ArrowUp,
 *                     contentDescription = null,
 *                 ),
 *             }
 *         ) {
 *             Text("Text")
 *         }
 *     },
 *     buttonVisible = visible,
 * ) {
 *     ...
 * }
 * ```
 */
@Composable
public fun PillButtonContainer(
    button: @Composable PillButtonContainerScope.() -> Unit,
    buttonVisible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        content()
        AnimatedVisibility(
            visible = buttonVisible,
            enter = fadeIn(
                tween(
                    durationMillis = ANIMATION_DURATION_MILLIS,
                    easing = EaseOutBack,
                ),
            ) + slideInVertically(
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS,
                    easing = EaseOutBack,
                ),
                initialOffsetY = { fullHeight -> -fullHeight },
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS,
                    easing = EaseInBack,
                ),
            ) + slideOutVertically(
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS,
                    easing = EaseInBack,
                ),
                targetOffsetY = { fullHeight -> -fullHeight },
            ),
        ) {
            PillButtonContainerScopeInstance.button()
        }
    }
}

/**
 * An elevated pill-shaped button that displays given [content] and (optionally) an icon.
 *
 * Must be hosted within a [PillButtonContainer] composable.
 *
 * ```
 * PillButton(
 *     onClick = { visible = false },
 *     icon = {
 *         Icon(
 *             painter = Icons.ArrowUp,
 *             contentDescription = null,
 *         ),
 *     },
 * )
 */
@Suppress("UnusedReceiverParameter")
@Composable
public fun PillButtonContainerScope.PillButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        color = OrbitTheme.colors.info.normal,
        shape = CircleShape,
        modifier = modifier
            .semantics { role = Role.Button }
            .padding(16.dp),
        elevation = OrbitTheme.elevations.Level1,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                icon()
                content()
            }
        }
    }
}

private const val ANIMATION_DURATION_MILLIS = 300

@LayoutScopeMarker
@Immutable
public interface PillButtonContainerScope

private object PillButtonContainerScopeInstance : PillButtonContainerScope

@OrbitPreviews
@Composable
internal fun PillButtonPreview() {
    Preview {
        PillButtonContainer(
            button = {
                PillButton(
                    onClick = { },
                    icon = { Icon(painter = Icons.ArrowUp, contentDescription = null) },
                ) {
                    Text("2 new results")
                }
            },
            buttonVisible = true,
            modifier = Modifier.fillMaxWidth(),
        ) { }

        PillButtonContainer(
            button = {
                PillButton(
                    onClick = { },
                ) {
                    Text("Iconless PillButton")
                }
            },
            buttonVisible = true,
            modifier = Modifier.fillMaxWidth(),
        ) { }
    }
}
