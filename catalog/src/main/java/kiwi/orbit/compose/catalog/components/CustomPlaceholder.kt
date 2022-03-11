package kiwi.orbit.compose.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
fun CustomPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(OrbitTheme.colors.primary.subtle),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Custom content",
            color = contentColorFor(backgroundColor = OrbitTheme.colors.primary.subtle)
        )
    }
}
