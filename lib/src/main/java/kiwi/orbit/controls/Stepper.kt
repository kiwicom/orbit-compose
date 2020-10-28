package kiwi.orbit.controls

import androidx.compose.foundation.AmbientIndication
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.icons.Icon
import kiwi.orbit.icons.Minus
import kiwi.orbit.icons.Plus
import androidx.compose.material.ButtonConstants as MaterialButtonConstants

@Composable
fun Stepper(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    check(value in minValue..maxValue) { "Provided value is out of required bounds." }

    Stepper(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        valueValidator = { newValue ->
            newValue in minValue..maxValue
        }
    )
}

@Composable
fun Stepper(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
    valueValidator: ((Int) -> Boolean)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            onClick = { onValueChange.invoke(value - 1) },
            enabled = valueValidator?.invoke(value - 1) ?: true,
        ) {
            Icon(Icon.Minus)
        }

        Text(
            text = value.toString(),
            style = OrbitTheme.typography.title4,
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSizeConstraints(44.dp),
        )

        StepperButton(
            onClick = { onValueChange.invoke(value + 1) },
            enabled = valueValidator?.invoke(value + 1) ?: true,
        ) {
            Icon(Icon.Plus)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun StepperButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionState = remember { InteractionState() }
    val colors = ButtonConstants.defaultButtonColors(
        backgroundColor = OrbitTheme.colors.surfaceSecondary,
    )
    val elevation = MaterialButtonConstants.defaultElevation(
        defaultElevation = 0.dp,
    )

    Surface(
        modifier = Modifier.clickable(
            onClick = onClick,
            enabled = enabled,
            interactionState = interactionState,
            indication = null,
        ),
        shape = MaterialTheme.shapes.small,
        color = colors.backgroundColor(enabled),
        contentColor = colors.contentColor(enabled),
        elevation = elevation.elevation(enabled, interactionState),
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.button
        ) {
            Row(
                Modifier
                    .indication(interactionState, AmbientIndication.current())
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                children = content,
            )
        }
    }
}