package kiwi.orbit.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.screens.AlertCardScreen
import kiwi.orbit.app.screens.ButtonsScreen
import kiwi.orbit.app.screens.ProfileScreen
import kiwi.orbit.app.screens.StepperScreen
import kiwi.orbit.app.screens.TextFieldScreen
import kiwi.orbit.app.ui.AppTheme

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalLayout::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLightTheme by remember { mutableStateOf(true) }
            AppTheme(isLightTheme = isLightTheme) {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(backgroundColor = OrbitTheme.colors.surfaceSecondary) {
                            Text(
                                text = "Compose Demo",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            IconButton(
                                onClick = {
                                    isLightTheme = !isLightTheme
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    Icons.Default.BrightnessMedium,
                                    contentDescription = "Toggle light/dark mode"
                                )
                            }
                        }
                    },
                    content = { MainContent() }
                )
            }
        }
    }
}

@Composable
fun MainContent() {
    val tabIndex = remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = tabIndex.value, backgroundColor = OrbitTheme.colors.surface) {
            AppTab("Profile", 0, tabIndex)
            AppTab("Buttons", 1, tabIndex)
            AppTab("TextField", 2, tabIndex)
            AppTab("Stepper", 3, tabIndex)
            AppTab("Alert", 4, tabIndex)
        }

        Box(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            when (tabIndex.value) {
                0 -> ProfileScreen()
                1 -> ButtonsScreen()
                2 -> TextFieldScreen()
                3 -> StepperScreen()
                4 -> AlertCardScreen()
            }
        }
    }
}


@Composable
fun AppTab(text: String, index: Int, tabIndex: MutableState<Int>) {
    Tab(selected = tabIndex.value == index, onClick = { tabIndex.value = index }) {
        Text(
            text,
            Modifier.padding(8.dp)
        )
    }
}
