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
import kiwi.orbit.compose.ui.foundation.contentColorFor
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
public fun BadgeItemNeutral(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeItem(icon, OrbitTheme.colors.surface.subtle, modifier, content)
}

@Composable
public fun BadgeItemInfo(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeItem(icon, OrbitTheme.colors.info.subtle, modifier, content)
}

@Composable
public fun BadgeItemSuccess(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeItem(icon, OrbitTheme.colors.success.subtle, modifier, content)
}

@Composable
public fun BadgeItemWarning(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeItem(icon, OrbitTheme.colors.warning.subtle, modifier, content)
}

@Composable
public fun BadgeItemCritical(
    icon: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BadgeItem(icon, OrbitTheme.colors.critical.subtle, modifier, content)
}

@Composable
private fun BadgeItem(
    icon: Painter,
    iconBackgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val iconTint = contentColorFor(iconBackgroundColor)
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
            BadgeItemInfo(Icons.Check) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeItemSuccess(Icons.Check) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeItemWarning(Icons.Check) {
                Text("This is a simple BadgeListItem. \nBut two rows are needed.")
            }
        }

        BadgeListSmall(contentEmphasis = ContentEmphasis.Minor) {
            BadgeItemNeutral(Icons.Check) {
                Text("This is a simple BadgeListItem.")
            }
            BadgeItemCritical(Icons.Check) {
                Text("This is a simple BadgeListItem.")
            }
        }
    }
}
