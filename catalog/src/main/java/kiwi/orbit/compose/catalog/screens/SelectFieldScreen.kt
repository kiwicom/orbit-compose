package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import coil.transition.CrossfadeTransition
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.SelectField
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun SelectFieldScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Select Field",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            SelectFieldScreenInner()
        }
    }
}

@Preview
@Composable
private fun SelectFieldScreenInner() {
    Column(Modifier.padding(16.dp)) {
        var selected by remember { mutableStateOf<Country?>(null) }
        val items = listOf(
            Country("Czechia", "https://images.kiwi.com/flags/32x0/flag-cz.jpg"),
            Country("Slovakia", "https://images.kiwi.com/flags/32x0/flag-sk.jpg"),
            Country("Germany", "https://images.kiwi.com/flags/32x0/flag-de.jpg"),
            Country("Austria", "https://images.kiwi.com/flags/32x0/flag-at.jpg"),
        )

        SelectField(
            value = selected?.name ?: "",
            options = items,
            error = when (selected?.name) {
                items.first().name -> {
                    { Text("Select other country.") }
                }
                else -> null
            },
            leadingIcon = selected?.flagUrl?.let { flagUrl ->
                {
                    CountryFlag(flagUrl)
                }
            },
            placeholder = { Text("Select country") },
            onOptionSelect = { selected = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth(),
        ) { country ->
            CountryFlag(country.flagUrl)
            Spacer(Modifier.size(16.dp))
            Text(country.name)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun CountryFlag(url: String) {
    Box(
        Modifier
            .width(24.dp)
            .border(0.5.dp, OrbitTheme.colors.surface.strong, RoundedCornerShape(2.dp))
            .padding(0.5.dp)
    ) {
        Image(
            painter = rememberImagePainter(url) {
                transition(CrossfadeTransition())
                size(OriginalSize)
                scale(Scale.FIT)
            },
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(2.dp)),
        )
    }
}

private class Country(
    val name: String,
    val flagUrl: String
)
