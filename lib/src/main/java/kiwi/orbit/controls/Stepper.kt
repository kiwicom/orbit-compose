package kiwi.orbit.controls

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.R
import kiwi.orbit.icons.Icons
import androidx.compose.material.ButtonDefaults as MaterialButtonDefaults

@Composable
public fun Stepper(
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
public fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    addContentDescription: String = stringResource(id = R.string.orbit_cd_stepper_add),
    removeContentDescription: String = stringResource(id = R.string.orbit_cd_stepper_remove),
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
            Icon(Icons.Minus, contentDescription = removeContentDescription)
        }

        Text(
            text = value.toString(),
            style = OrbitTheme.typography.title4,
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSize(44.dp),
        )

        StepperButton(
            onClick = { onValueChange.invoke(value + 1) },
            enabled = valueValidator?.invoke(value + 1) ?: true,
        ) {
            Icon(Icons.Plus, contentDescription = addContentDescription)
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
    val interactionSource = remember { MutableInteractionSource() }
    val colors = ButtonConstants.defaultButtonColors(
        backgroundColor = OrbitTheme.colors.surfaceAlt,
    )
    val elevation = MaterialButtonDefaults.elevation(
        defaultElevation = 1.dp,
    )
    val contentColor by colors.contentColor(enabled)

    Surface(
        modifier = Modifier.clickable(
            onClick = onClick,
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null,
        ),
        shape = OrbitTheme.shapes.small,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        elevation = elevation.elevation(enabled, interactionSource).value,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(MaterialTheme.typography.button) {
                Row(
                    Modifier
                        .indication(interactionSource, LocalIndication.current)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content,
                )
            }
        }
    }
}
