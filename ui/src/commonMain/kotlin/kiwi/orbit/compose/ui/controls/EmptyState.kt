package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun EmptyState(
    illustration: Painter,
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    action: (@Composable () -> Unit)? = null,
) {
    EmptyState(
        illustration = { Image(painter = illustration, contentDescription = null) },
        title = { Text(title) },
        modifier = modifier,
        description = description?.let { { Text(description) } },
        action = action,
    )
}

@Composable
public fun EmptyState(
    illustration: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    description: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.height(120.dp),
            propagateMinConstraints = true,
        ) {
            illustration()
        }

        Spacer(modifier = Modifier.size(16.dp))

        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.title4.copy(textAlign = TextAlign.Center),
        ) {
            title()
        }

        if (description != null) {
            Spacer(modifier = Modifier.size(8.dp))

            CompositionLocalProvider(
                LocalTextStyle provides OrbitTheme.typography.bodyNormal.copy(textAlign = TextAlign.Center),
                LocalContentEmphasis provides ContentEmphasis.Minor,
            ) {
                description()
            }
        }

        if (action != null) {
            Spacer(modifier = Modifier.size(16.dp))
            action()
        }
    }
}

@OrbitPreviews
@Composable
internal fun EmptyStatePreview() {
    Preview {
        EmptyState(
            illustration = Icons.Accommodation, // illustrations are not a dependency of :ui
            title = "No Results Found",
            description = "We did not find any flights.",
            action = {
                ButtonPrimary(onClick = {}) {
                    Text("Try again")
                }
            },
        )
    }
}
