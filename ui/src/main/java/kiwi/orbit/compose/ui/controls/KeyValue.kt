package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun KeyValue(
    key: @Composable () -> Unit,
    value: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodySmall,
            LocalContentEmphasis provides ContentEmphasis.Minor,
        ) {
            key()
        }
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodyNormalMedium,
            LocalContentEmphasis provides ContentEmphasis.Normal,
        ) {
            value()
        }
    }
}

@Composable
public fun KeyValueLarge(
    key: @Composable () -> Unit,
    value: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodyNormal,
            LocalContentEmphasis provides ContentEmphasis.Minor,
        ) {
            key()
        }
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodyLargeMedium,
            LocalContentEmphasis provides ContentEmphasis.Normal,
        ) {
            value()
        }
    }
}

@Composable
public fun KeyValue(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    KeyValue(
        key = { Text(text = key) },
        value = { Text(text = value) },
        modifier = modifier,
    )
}

@Composable
public fun KeyValueLarge(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    KeyValueLarge(
        key = { Text(text = key) },
        value = { Text(text = value) },
        modifier = modifier,
    )
}

@Preview
@Composable
internal fun KeyValuePreview() {
    Preview {
        KeyValue(
            key = "Key",
            value = "Value",
        )
        KeyValueLarge(
            key = "Large key",
            value = "Large value",
        )
    }
}
