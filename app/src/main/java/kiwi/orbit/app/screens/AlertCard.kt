package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.controls.AlertCriticalCard
import kiwi.orbit.controls.AlertInfoCard
import kiwi.orbit.controls.AlertSuccessCard
import kiwi.orbit.controls.AlertWarningCard
import kiwi.orbit.controls.ButtonPrimary
import kiwi.orbit.controls.ButtonPrimarySubtle

@Preview
@Composable
fun AlertCardScreen() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            AlertSuccessCard(Modifier.fillMaxWidth()) {
                Text("Your payment was successful.")
                Spacer(Modifier.height(12.dp))
                Text("View the receipt in the profile:")
                Spacer(Modifier.height(4.dp))
                Row {
                    ButtonPrimary(onClick = {}) {
                        Text("Show receipt")
                    }
                    Spacer(Modifier.width(8.dp))
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Share receipt")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertInfoCard(Modifier.fillMaxWidth()) {
                Text("Re-check your credentials")
                Spacer(Modifier.height(12.dp))
                Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.")
                Spacer(Modifier.height(4.dp))
                Row {
                    ButtonPrimary(onClick = {}) {
                        Text("More info")
                    }
                    Spacer(Modifier.width(8.dp))
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Mark as checked")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertWarningCard(Modifier.fillMaxWidth()) {
                Text("Visa requirements check")
                Spacer(Modifier.height(12.dp))
                Text("The requirements found here are for reference purposes only. Contact the embassy or your foreign ministry for more information.")
                Spacer(Modifier.height(4.dp))
                Row {
                    ButtonPrimary(onClick = {}) {
                        Text("Check requirements")
                    }
                    Spacer(Modifier.width(8.dp))
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Mark as checked")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertCriticalCard(Modifier.fillMaxWidth()) {
                Text("No results loaded")
                Spacer(Modifier.height(12.dp))
                Text(" There was an error while processing your request. Refresh the page to load the results.")
                Spacer(Modifier.height(4.dp))
                Row {
                    ButtonPrimary(onClick = {}) {
                        Text("Refresh page")
                    }
                    Spacer(Modifier.width(8.dp))
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Contact support")
                    }
                }
            }

        }
    }
}
