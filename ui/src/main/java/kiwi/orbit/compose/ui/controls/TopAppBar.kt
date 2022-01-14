package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.contentColorFor
import com.google.accompanist.insets.ui.TopAppBar as AccompanistTopAppBar

@Composable
public fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 2.dp,
) {
    AccompanistTopAppBar(
        title = {
            ProvideMergedTextStyle(
                value = TextStyle(
                    fontSize = 19.sp,
                    letterSpacing = 0.15.sp,
                    fontWeight = FontWeight.Medium,
                )
            ) {
                CompositionLocalProvider(
                    LocalContentEmphasis provides ContentEmphasis.Normal
                ) {
                    Box(
                        Modifier.semantics(mergeDescendants = true) {
                            testTag = TopAppBarSemantics.TitleTag
                        }
                    ) {
                        title()
                    }
                }
            }
        },
        modifier = modifier.testTag(TopAppBarSemantics.Tag),
        navigationIcon = if (navigationIcon != null) {
            {
                CompositionLocalProvider(
                    LocalContentEmphasis provides ContentEmphasis.Normal
                ) {
                    navigationIcon.invoke()
                }
            }
        } else null,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false
        ),
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
    )
}

@Composable
public fun TopAppBar(
    title: @Composable () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(R.drawable.ic_appbar_arrow_back),
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
            ) {
                Icon(
                    painter = icon,
                    contentDescription = stringResource(R.string.orbit_cd_navigate_up),
                )
            }
        },
        actions = actions,
    )
}

public object TopAppBarSemantics {
    public const val Tag: String = "top_app_bar"
    public const val TitleTag: String = "title"
}
