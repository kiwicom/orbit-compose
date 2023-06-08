package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

/**
 * Displays a simple list of items with customisable icons.
 * Modify via passing a custom [modifier].
 *
 * Hosts the [ListItem] Composables.
 *
 * Minimal example:
 *
 * ```
 * List {
 *     ListItem { Text("First item") }
 *     ListItem { Text("Second item") }
 *     ListItem { Text("Third item") }
 * }
 * ```
 *
 * List with custom icons example:
 *
 * ```
 * List {
 *     ListItem(
 *         icon = { Icon(painter = Icons.BaggagePersonal, contentDescription = null) },
 *         content = { Text("Personal Item") },
 *     ),
 *     ListItem(
 *         icon = Icons.BaggageCabin,
 *         content = { Text("Cabin Baggage") },
 *     )
 *     ListItem(
 *         icon = Icons.BaggageChecked20,
 *         content = { Text("Checked Baggage") },
 *     )
 * }
 * ```
 *
 * List with default icon and icon color that can be overridden by ListItem properties:
 *
 * ```
 * List(
 *     iconTint = OrbitTheme.colors.primary.normal,
 *     icon = Icons.CircleEmpty,
 * ) {
 *     // Resolves to: default icon, default color.
 *     ListItem { Text("First item") }
 *
 *     // Resolves to: custom icon, default color.
 *     ListItem(
 *         icon = { Icon(painter = Icons.Check, contentDescription = null) },
 *         content = { Text("Checked Baggage") },
 *     )
 *
 *     // Resolves to: default icon, custom color.
 *     ListItem(
 *         iconTint = OrbitTheme.colors.critical.normal,
 *         content = { Text("Checked Baggage") },
 *     )
 *
 *     // Resolves to: custom icon, custom color.
 *     ListItem(
 *         iconTint = OrbitTheme.colors.critical.normal,
 *         icon = { Icon(painter = Icons.CloseCircle, contentDescription = null) },
 *         content = { Text("Personal Item") },
 *     )
 * }
 * ```
 */
@Composable
public fun List(
    modifier: Modifier = Modifier,
    contentColor: Color = OrbitTheme.colors.content.normal,
    iconContentDescription: String? = null,
    iconTint: Color = Color.Unspecified,
    icon: Painter = Icons.CircleSmall,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp, Alignment.Top),
    content: @Composable ListScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyNormal,
    ) {
        ListPrimitive(
            contentColor = contentColor,
            iconContentDescription = iconContentDescription,
            iconTint = iconTint,
            icon = icon,
            verticalArrangement = verticalArrangement,
            content = content,
            modifier = modifier,
        )
    }
}

/**
 * Displays a simple list of items with customisable icons using a larger font.
 * Modify via passing a custom [modifier].
 *
 * Hosts the [ListItem] Composables.
 *
 * Minimal example:
 *
 * ```
 * ListLarge {
 *     ListItem { Text("First item") }
 *     ListItem { Text("Second item") }
 *     ListItem { Text("Third item") }
 * }
 * ```
 *
 * List with custom icons example:
 *
 * ```
 * ListLarge {
 *     ListItem(
 *         icon = { Icon(painter = Icons.BaggagePersonal, contentDescription = null) },
 *         content = { Text("Personal Item") },
 *     ),
 *     ListItem(
 *         icon = Icons.BaggageCabin,
 *         content = { Text("Cabin Baggage") },
 *     )
 *     ListItem(
 *         icon = Icons.BaggageChecked20,
 *         content = { Text("Checked Baggage") },
 *     )
 * }
 * ```
 *
 * List with default icon and icon color that can be overridden by ListItem properties:
 *
 * ```
 * ListLarge(
 *     iconTint = OrbitTheme.colors.primary.normal,
 *     icon = Icons.CircleEmpty,
 * ) {
 *     // Resolves to: default icon, default color.
 *     ListItem { Text("First item") }
 *
 *     // Resolves to: custom icon, default color.
 *     ListItem(
 *         icon = { Icon(painter = Icons.Check, contentDescription = null) },
 *         content = { Text("Checked Baggage") },
 *     )
 *
 *     // Resolves to: default icon, custom color.
 *     ListItem(
 *         iconTint = OrbitTheme.colors.critical.normal,
 *         content = { Text("Checked Baggage") },
 *     )
 *
 *     // Resolves to: custom icon, custom color.
 *     ListItem(
 *         iconTint = OrbitTheme.colors.critical.normal,
 *         icon = { Icon(painter = Icons.CloseCircle, contentDescription = null) },
 *         content = { Text("Personal Item") },
 *     )
 * }
 * ```
 */
@Composable
public fun ListLarge(
    modifier: Modifier = Modifier,
    contentColor: Color = OrbitTheme.colors.content.normal,
    iconContentDescription: String? = null,
    iconTint: Color = Color.Unspecified,
    icon: Painter = Icons.CircleSmall,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp, Alignment.Top),
    content: @Composable ListScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyLarge,
    ) {
        ListPrimitive(
            contentColor = contentColor,
            iconContentDescription = iconContentDescription,
            iconTint = iconTint,
            icon = icon,
            verticalArrangement = verticalArrangement,
            content = content,
            modifier = modifier,
        )
    }
}

/**
 * A single list item with customizable icon.
 *
 * [icon] override default icon provided by [ListScope].
 */
@Composable
public fun ListScope.ListItem(
    modifier: Modifier = Modifier,
    iconTint: Color = this.iconTint,
    icon: @Composable () -> Unit = {
        Icon(
            painter = this.icon,
            contentDescription = this.iconContentDescription,
        )
    },
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val currentContentColor = LocalContentColor.current
        CompositionLocalProvider(
            LocalContentColor provides iconTint.takeOrElse { currentContentColor },
        ) {
            icon()
        }
        content()
    }
}

@Composable
private fun ListPrimitive(
    contentColor: Color,
    iconContentDescription: String?,
    iconTint: Color,
    icon: Painter,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable ListScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
        ) {
            val listScope = remember(iconContentDescription, icon) {
                ListScope(
                    iconContentDescription = iconContentDescription,
                    iconTint = iconTint,
                    icon = icon,
                )
            }
            listScope.content()
        }
    }
}

@LayoutScopeMarker
@Immutable
public class ListScope internal constructor(
    public val iconContentDescription: String?,
    public val iconTint: Color,
    public val icon: Painter,
)

@OrbitPreviews
@Composable
internal fun ListPreview() {
    Preview {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            List {
                ListItem { Text("This is simple list item") }
                ListItem { Text("This is another simple list item") }
                ListItem { Text("This list item\nspans over multiple lines") }
            }

            List {
                ListItem(
                    icon = { Icon(painter = Icons.ChildFriendly, contentDescription = null) },
                    content = { Text("Child-friendly") },
                )
                ListItem(
                    icon = {
                        Icon(
                            painter = Icons.Pet,
                            contentDescription = null,
                            tint = OrbitTheme.colors.critical.normal,
                        )
                    },
                    content = { Text("Pets not allowed") },
                )
            }

            List(
                contentColor = OrbitTheme.colors.critical.normal,
            ) {
                ListItem { Text("First thing that went wrong") }
                ListItem { Text("Second thing that went wrong") }
                ListItem { Text("Third thing that went wrong") }
            }

            List(
                iconTint = OrbitTheme.colors.success.normal,
                icon = Icons.CheckCircle,
            ) {
                ListItem {
                    Text("This checks.")
                }
                ListItem(
                    icon = { Icon(painter = Icons.Check, contentDescription = null) },
                ) {
                    Text("This is also right.")
                }
                ListItem(
                    iconTint = OrbitTheme.colors.critical.normal,
                    icon = { Icon(painter = Icons.CloseCircle, contentDescription = null) },
                ) {
                    Text("This is wrong!")
                }
            }
        }
    }
}

@OrbitPreviews
@Composable
internal fun ListLargePreview() {
    Preview {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ListLarge {
                ListItem { Text("This is simple list item") }
                ListItem { Text("This is another simple list item") }
                ListItem { Text("This list item\nspans over multiple lines") }
            }

            ListLarge {
                ListItem(
                    icon = { Icon(painter = Icons.Wifi, contentDescription = null) },
                    content = { Text("Wi-Fi available") },
                )
                ListItem(
                    icon = {
                        Icon(
                            painter = Icons.Wifi,
                            contentDescription = null,
                            tint = OrbitTheme.colors.critical.normal,
                        )
                    },
                    content = { Text("Wi-Fi not available") },
                )
            }

            ListLarge(
                contentColor = OrbitTheme.colors.success.strong,
            ) {
                ListItem { Text("First thing that went well") }
                ListItem { Text("Second thing that went well") }
                ListItem { Text("Third thing that went well") }
            }

            ListLarge(
                iconTint = OrbitTheme.colors.success.normal,
                icon = Icons.CheckCircle,
            ) {
                ListItem {
                    Text("This checks.")
                }
                ListItem(
                    icon = { Icon(painter = Icons.Check, contentDescription = null) },
                ) {
                    Text("This is also right.")
                }
                ListItem(
                    iconTint = OrbitTheme.colors.critical.normal,
                    icon = { Icon(painter = Icons.CloseCircle, contentDescription = null) },
                ) {
                    Text("This is wrong!")
                }
            }
        }
    }
}
