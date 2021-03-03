package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.controls.AlertButtons
import kiwi.orbit.controls.AlertCritical
import kiwi.orbit.controls.AlertInfo
import kiwi.orbit.controls.AlertSuccess
import kiwi.orbit.controls.AlertWarning
import kiwi.orbit.controls.ButtonPrimary
import kiwi.orbit.controls.ButtonPrimarySubtle

@Preview
@Composable
fun AlertScreen() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {


            AlertInfo(Modifier.fillMaxWidth()) {
                Text("Re-check your credentials")
                Spacer(Modifier.height(4.dp))
                Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.")

                AlertButtons {
                    ButtonPrimary(onClick = {}) {
                        Text("More info")
                    }
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Mark as checked")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertSuccess(Modifier.fillMaxWidth()) {
                Text("Your payment was successful.")
                Spacer(Modifier.height(4.dp))
                Text("View the receipt in the profile:")

                AlertButtons {
                    ButtonPrimary(onClick = {}) {
                        Text("Show receipt")
                    }
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Share receipt")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertWarning(Modifier.fillMaxWidth()) {
                Text("Visa requirements check")
                Spacer(Modifier.height(4.dp))
                Text("The requirements found here are for reference purposes only. Contact the embassy or your foreign ministry for more information.")

                AlertButtons {
                    ButtonPrimary(onClick = {}) {
                        Text("Check requirements")
                    }
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Mark as checked")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertCritical(Modifier.fillMaxWidth()) {
                Text("No results loaded")
                Spacer(Modifier.height(4.dp))
                Text("There was an error while processing your request. Refresh the page to load the results.")

                AlertButtons {
                    ButtonPrimary(onClick = {}) {
                        Text("Refresh page")
                    }
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Contact support")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Alternative versions")
            Spacer(Modifier.height(8.dp))

            AlertInfo(Modifier.fillMaxWidth()) {
                Text("Re-check your credentials")
                Spacer(Modifier.height(4.dp))
                Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.")

                AlertButtons {
                    ButtonPrimary(onClick = {}) {
                        Text("More info")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AlertInfo(Modifier.fillMaxWidth()) {
                Text("Re-check your credentials")
                Spacer(Modifier.height(4.dp))
                Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.")

                AlertButtons {
                    ButtonPrimarySubtle(onClick = {}) {
                        Text("Mark as checked")
                    }
                }
            }
        }
    }
}
