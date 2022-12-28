package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.layout.size

@Composable
public fun BadgeList(
    modifier: Modifier = Modifier,
    contentEmphasis: ContentEmphasis = ContentEmphasis.Normal,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
        ProvideContentEmphasis(contentEmphasis) {
            Column(
                modifier = modifier,
                verticalArrangement = verticalArrangement,
                content = content,
            )
        }
    }
}

@Composable
public fun BadgeListSmall(
    modifier: Modifier = Modifier,
    contentEmphasis: ContentEmphasis = ContentEmphasis.Normal,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    ProvideMergedTextStyle(OrbitTheme.typography.bodySmall) {
        ProvideContentEmphasis(contentEmphasis) {
            Column(
                modifier = modifier,
                verticalArrangement = verticalArrangement,
                content = content,
            )
        }
    }
}

@Composable
public fun BadgeListItemNeutral(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeListItem(
        icon = icon,
        iconBackgroundColor = OrbitTheme.colors.surface.subtle,
        iconTint = OrbitTheme.colors.content.minor,
        modifier = modifier,
        content = content,
    )
}

@Composable
public fun BadgeListItemInfo(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeListItem(
        icon = icon,
        iconBackgroundColor = OrbitTheme.colors.info.subtle,
        iconTint = OrbitTheme.colors.info.normal,
        modifier = modifier,
        content = content,
    )
}

@Composable
public fun BadgeListItemSuccess(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeListItem(
        icon = icon,
        iconBackgroundColor = OrbitTheme.colors.success.subtle,
        iconTint = OrbitTheme.colors.success.normal,
        modifier = modifier,
        content = content,
    )
}

@Composable
public fun BadgeListItemWarning(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeListItem(
        icon = icon,
        iconBackgroundColor = OrbitTheme.colors.warning.subtle,
        iconTint = OrbitTheme.colors.warning.normal,
        modifier = modifier,
        content = content,
    )
}

@Composable
public fun BadgeListItemCritical(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeListItem(
        icon = icon,
        iconBackgroundColor = OrbitTheme.colors.critical.subtle,
        iconTint = OrbitTheme.colors.critical.normal,
        modifier = modifier,
        content = content,
    )
}

@Composable
private fun BadgeListItem(
    icon: Painter,
    iconBackgroundColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .background(iconBackgroundColor, shape = CircleShape)
                .padding(4.dp)
                .size(16.sp),
        )
        val padding = (24f - LocalTextStyle.current.lineHeight.value).div(2).dp
        Box(
            modifier = Modifier.padding(padding),
            content = { content() },
        )
    }
}

@Composable
@OrbitPreviews
internal fun BadgeListPreview() {
    Preview {
        BadgeList {
            BadgeListItemInfo(Icons.BaggageCabin) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeListItemSuccess(Icons.BaggageCabin) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeListItemWarning(Icons.BaggageCabin) {
                Text("This is a simple BadgeListItem. \nBut two rows are needed.")
            }
        }

        BadgeListSmall(contentEmphasis = ContentEmphasis.Minor) {
            BadgeListItemNeutral(Icons.BaggageCabin) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeListItemCritical(Icons.BaggageCabin) {
                Text("This is a simple BadgeListItem.")
            }
        }
    }
}
