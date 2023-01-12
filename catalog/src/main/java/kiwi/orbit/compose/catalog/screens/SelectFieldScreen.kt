package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.CountryFlag
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.SelectField
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun SelectFieldScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Field") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            SelectFieldScreenInner()
        }
    }
}

@Preview
@Composable
private fun SelectFieldScreenInner() {
    Column(Modifier.padding(16.dp)) {
        var selected by rememberSaveable { mutableStateOf<Country?>(null) }
        val items = listOf(
            Country("Czechia", "cz"),
            Country("Slovakia", "sk"),
            Country("Germany", "de"),
            Country("Austria", "at"),
            Country("Poland", "pl"),
            Country("Invalid", "invalid"),
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
            leadingIcon = selected?.let { selectedItem ->
                {
                    CountryFlag(selectedItem.code, contentDescription = selectedItem.name)
                }
            },
            placeholder = { Text("Select country") },
            onOptionSelect = { selected = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth(),
        ) { country ->
            CountryFlag(country.code, country.name)
            Spacer(Modifier.size(16.dp))
            Text(country.name)
        }
    }
}

private class Country(
    val name: String,
    val code: String,
)
