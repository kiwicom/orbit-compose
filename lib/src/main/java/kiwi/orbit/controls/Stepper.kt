package kiwi.orbit.controls

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonConstants
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.contentColorFor
import kiwi.orbit.icons.Icon
import kiwi.orbit.icons.Minus
import kiwi.orbit.icons.Plus

@Composable
fun Stepper(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChanged: ((Int) -> Unit)? = null,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    check(value in minValue..maxValue) { "Provided value is out of required bounds." }

    Stepper(
        value = value,
        modifier = modifier,
        onValueChanged = onValueChanged,
        valueValidator = { newValue ->
            newValue in minValue..maxValue
        }
    )
}

@Composable
fun Stepper(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChanged: ((Int) -> Unit)? = null,
    valueValidator: ((Int) -> Boolean)? = null,
) {
    Row(
        modifier = modifier,
        verticalGravity = Alignment.CenterVertically,
    ) {
        StepperButton(
            onClick = { onValueChanged?.invoke(value - 1) },
            enabled = valueValidator?.invoke(value - 1) ?: true,
        ) {
            Icon(Icon.Minus)
        }

        Text(
            text = value.toString(),
            style = OrbitTheme.typography.title4,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .defaultMinSizeConstraints(44.dp),
        )

        StepperButton(
            onClick = { onValueChanged?.invoke(value + 1) },
            enabled = valueValidator?.invoke(value + 1) ?: true,
        ) {
            Icon(Icon.Plus)
        }
    }
}

@Composable
private fun StepperButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionState = remember { InteractionState() }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (enabled) OrbitTheme.colors.surfaceSecondary else OrbitTheme.colors.surfaceDisabled,
        contentColor = if (enabled) contentColorFor(OrbitTheme.colors.surfaceSecondary) else ButtonConstants.defaultDisabledContentColor,
        elevation = if (enabled) 1.dp else 0.dp,
        modifier = Modifier.clickable(
            onClick = onClick,
            enabled = enabled,
            interactionState = interactionState,
            indication = null,
        )
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.button
        ) {
            Row(
                Modifier
                    .indication(interactionState, IndicationAmbient.current())
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalGravity = Alignment.CenterVertically,
                children = content,
            )
        }
    }
}
