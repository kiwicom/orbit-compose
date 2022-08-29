package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import androidx.compose.ui.window.Dialog as ComposeDialog

/**
 * Draws an Orbit-themed modal dialog with either one or two actions.
 *
 * According to Orbit, one should use `ButtonPrimary()` for the primary action or `ButtonCritical()` provided
 * that the primary action is destructive in its nature. Put the primary action button in the `confirmButton`
 * slot.
 *
 * Secondary action should use `ButtonLinkPrimary()` or `ButtonLinkCritical()` respectively. In certain cases,
 * one may also use `ButtonLinkSecondary()`. Put the secondary action button in the `dismissButton` slot.
 *
 * Example:
 *
 * ```
 * Dialog(
 *   onDismissRequest = {},
 *   title = { Text("Title") },
 *   text = { Text("Text") },
 *   confirmButton = {
 *     ButtonPrimary(
 *       modifier = Modifier.fillMaxWidth(),
 *     ) {
 *       Text("Confirm")
 *     }
 *   },
 *   dismissButton = {
 *     ButtonLinkSecondary(
 *       onClick = {},
 *       modifier = Modifier.fillMaxWidth(),
 *     ) {
 *       Text("Dismiss")
 *     }
 *   },
 *   illustration = {
 *     Image(
 *       painter = Illustrations.NoNotification,
 *       contentDescription = null,
 *       modifier = Modifier.fillMaxWidth(0.64f),
 *     )
 *   },
 * )
 * ```
 */
@Composable
public fun Dialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit = {},
    illustration: @Composable () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        text = text,
        buttons = {
            confirmButton()
            dismissButton()
        },
        illustration = illustration,
        properties = properties,
        title = title,
    )
}

/**
 * Draws an Orbit-themed modal dialog with multiple actions.
 *
 * According to Orbit, one should use `ButtonPrimary()` for the primary action or `ButtonCritical()` provided
 * that the primary action is destructive in its nature.
 *
 * One should use `ButtonLinkPrimary()` or `ButtonLinkCritical()` for secondary actions. In certain cases,
 * one may also use `ButtonLinkSecondary()`.
 *
 * Actions are displayed in a consecutive manner.
 *
 * Example:
 *
 * ```
 * Dialog(
 *   onDismissRequest = {},
 *   title = { Text("Title") },
 *   text = { Text("Text") },
 *   buttons = {
 *     ButtonPrimary(
 *       modifier = Modifier.fillMaxWidth(),
 *     ) {
 *       Text("Confirm")
 *     }
 *
 *     ButtonLinkPrimary(
 *       onClick = {},
 *       modifier = Modifier.fillMaxWidth(),
 *     ) {
 *       Text("Maybe later")
 *     }
 *
 *     ButtonLinkSecondary(
 *       onClick = {},
 *       modifier = Modifier.fillMaxWidth(),
 *     ) {
 *       Text("""Don't show again""")
 *     }
 *   },
 *   illustration = {
 *     Image(
 *       painter = Illustrations.NoNotification,
 *       contentDescription = null,
 *       modifier = Modifier.fillMaxWidth(0.64f),
 *     )
 *   },
 * )
 * ```
 */
@Composable
public fun Dialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    buttons: @Composable ColumnScope.() -> Unit,
    illustration: @Composable () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
) {
    ComposeDialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        DialogContent(
            title = title,
            text = text,
            buttons = buttons,
            illustration = illustration,
        )
    }
}

@Composable
private fun DialogContent(
    illustration: @Composable () -> Unit,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    buttons: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        shape = OrbitTheme.shapes.large,
        elevation = OrbitTheme.elevations.Level2,
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            illustration()

            Header(
                title = title,
                description = text,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                buttons()
            }
        }
    }
}

@Composable
private fun Header(
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.title4,
        ) {
            title()
        }
        CompositionLocalProvider(
            LocalContentEmphasis provides ContentEmphasis.Minor,
        ) {
            description()
        }
    }
}

@OrbitPreviews
@Composable
private fun DialogFullPreview() {
    Preview {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Dialog(
                onDismissRequest = {},
                text = { Text("Description") },
                confirmButton = {
                    ButtonPrimary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    ButtonLinkSecondary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Dismiss")
                    }
                },
                title = { Text("Title") },
            )
        }
    }
}

@OrbitPreviews
@Composable
private fun DialogMultipleActionsFullPreview() {
    Preview {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Dialog(
                onDismissRequest = {},
                title = { Text("Title") },
                text = { Text("Description") },
                buttons = {
                    ButtonPrimary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Confirm")
                    }
                    ButtonLinkCritical(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Delete")
                    }
                    ButtonLinkSecondary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Dismiss")
                    }
                },
            )
        }
    }
}

@OrbitPreviews
@Composable
internal fun DialogPreview() {
    Preview {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            DialogContent(
                illustration = {
                    Box(
                        modifier = Modifier
                            .background(OrbitTheme.colors.primary.subtle)
                            .fillMaxWidth(0.64f)
                            .height(90.dp),
                    ) {}
                },
                title = { Text("Title") },
                text = { Text("Description") },
                buttons = {
                    ButtonPrimary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Confirm")
                    }
                    ButtonLinkCritical(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Delete")
                    }
                    ButtonLinkPrimary(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Dismiss")
                    }
                },
            )
        }
    }
}
