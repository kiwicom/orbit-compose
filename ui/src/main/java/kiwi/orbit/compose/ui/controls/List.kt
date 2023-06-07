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
 *
 * List with default icon and icon color, that can be overridden by ListItem properties:
 *
 * ```
 * List(
 *     defaultIconColor = OrbitTheme.colors.primary.normal,
 *     defaultIcon = Icons.CircleEmpty,
 *     defaultContentDescription = null,
 * ) {
 *     // Resolves to: default icon, default color, default description.
 *     ListItem { Text("First item") }
 *
 *     // Resolves to: custom icon and description, default color.
 *     ListItem(
 *         icon = Icons.BaggageChecked20,
 *         contentDescription = null,
 *         content = { Text("Checked Baggage") },
 *     )
 *
 *     // Resolves to: custom icon composable, defaults ignored.
 *     ListItem(
 *         icon = { Icon(painter = Icons.BaggagePersonal, contentDescription = null, tint = Color.Blue) },
 *         content = { Text("Personal Item") },
 *     )
 * {
 */
@Composable
public fun List(
    modifier: Modifier = Modifier,
    contentColor: Color = OrbitTheme.colors.content.normal,
    defaultContentDescription: String? = null,
    defaultIconColor: Color? = null,
    defaultIcon: Painter = Icons.CircleSmall,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp, Alignment.Top),
    content: @Composable ListScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyNormal,
    ) {
        ListPrimitive(
            contentColor = contentColor,
            defaultContentDescription = defaultContentDescription,
            defaultIconColor = defaultIconColor,
            defaultIcon = defaultIcon,
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
 *         icon = { Icon(painter = Icons.BaggageCabin, contentDescription = null) },
 *         content = { Text("Cabin Baggage") },
 *     )
 *     ListItem(
 *         icon = { Icon(painter = Icons.BaggageChecked20, contentDescription = null) },
 *         content =  { Text("Checked Baggage") },
 *     )
 * }
 * ```
 */
@Composable
public fun ListLarge(
    modifier: Modifier = Modifier,
    contentColor: Color = OrbitTheme.colors.content.normal,
    defaultContentDescription: String? = null,
    defaultIconColor: Color? = null,
    defaultIcon: Painter = Icons.CircleSmall,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp, Alignment.Top),
    content: @Composable ListScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides OrbitTheme.typography.bodyLarge,
    ) {
        ListPrimitive(
            contentColor = contentColor,
            defaultContentDescription = defaultContentDescription,
            defaultIconColor = defaultIconColor,
            defaultIcon = defaultIcon,
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
    icon: @Composable () -> Unit = {
        if (this.iconColor != null) {
            Icon(
                painter = this.icon,
                tint = this.iconColor,
                contentDescription = this.contentDescription,
            )
        } else {
            Icon(
                painter = this.icon,
                contentDescription = this.contentDescription,
            )
        }

    },
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        icon()
        content()
    }
}

/**
 * A single list item with separately customizable icon painter and icon tint.
 *
 * [icon] override default icon painter provided by [ListScope].
 * [contentDescription] override default description provided by [ListScope].
 * [iconColor] override default icon color provided by [ListScope]
 */
@Composable
public fun ListScope.ListItem(
    modifier: Modifier = Modifier,
    icon: Painter = this.icon,
    contentDescription: String? = this.contentDescription,
    iconColor: Color? = this.iconColor,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (iconColor != null) {
            Icon(
                painter = icon,
                tint = iconColor,
                contentDescription = contentDescription,
            )
        } else {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
            )
        }
        content()
    }
}

@Composable
private fun ListPrimitive(
    contentColor: Color,
    defaultContentDescription: String?,
    defaultIconColor: Color?,
    defaultIcon: Painter,
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
            val listScope = remember(defaultContentDescription, defaultIcon) {
                ListScope(
                    contentDescription = defaultContentDescription,
                    iconColor = defaultIconColor,
                    icon = defaultIcon,
                )
            }
            listScope.content()
        }
    }
}

@LayoutScopeMarker
@Immutable
public class ListScope internal constructor(
    public val contentDescription: String?,
    public val iconColor: Color?,
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
                defaultIconColor = OrbitTheme.colors.success.normal,
                defaultIcon = Icons.QuestionCircle,
            ) {
                ListItem(
                    icon = Icons.CheckCircle,
                ) {
                    Text("This checks.")
                }
                ListItem(
                    iconColor = OrbitTheme.colors.critical.normal,
                    icon = Icons.CloseCircle,
                ) {
                    Text("This is wrong!")
                }
                ListItem(
                    iconColor = OrbitTheme.colors.info.normal,
                ) {
                    Text("This is uncertain.")
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
                defaultIconColor = OrbitTheme.colors.success.normal,
                defaultIcon = Icons.CheckCircle,
            ) {
                ListItem {
                    Text("This checks.")
                }
                ListItem(icon = Icons.Check) {
                    Text("This is also right.")
                }
                ListItem(
                    iconColor = OrbitTheme.colors.critical.normal,
                    icon = Icons.CloseCircle,
                ) {
                    Text("This is wrong!")
                }
                ListItem(
                    icon = { Icon(painter = Icons.QuestionCircle, contentDescription = null) },
                ) {
                    Text("This is uncertain.")
                }
            }
        }
    }
}
