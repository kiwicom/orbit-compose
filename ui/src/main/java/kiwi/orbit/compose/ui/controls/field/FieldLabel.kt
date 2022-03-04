package kiwi.orbit.compose.ui.controls.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
internal fun FieldLabel(
    content: @Composable () -> Unit
) {
    ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
        Box(Modifier.padding(bottom = 4.dp)) {
            content()
        }
    }
}
