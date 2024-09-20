package net.evan1026.ledcontrol.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import kotlinx.coroutines.launch

@Composable
private fun BrightnessButton(
    text: String,
    brightness: Int
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch {
                setBrightness(brightness)
            }
        }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BrightButton() {
    BrightnessButton("Bright", 255)
}

@Composable
fun DimButton() {
    BrightnessButton("Dim", 75)
}

@Composable
fun OffButton() {
    BrightnessButton("Off", 0)
}

@Composable
fun BrightnessButtonsRow() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BrightButton()
        DimButton()
        OffButton()
    }
}