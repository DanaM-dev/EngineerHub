package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun calculateVoltage(current: Double, resistance: Double): Double = current * resistance
fun calculateSeriesResistance(r1: Double, r2: Double): Double = r1 + r2

@Composable
fun VoltageCalculator() {
    var currentInput by remember { mutableStateOf("") }
    var resistanceInput by remember { mutableStateOf("") }
    var voltageResult by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Ohm's Law",
        subtitle = "Calculate Voltage (V = I × R)",
        onCalculate = {
            val current = currentInput.toDoubleOrNull()
            val resistance = resistanceInput.toDoubleOrNull()
            if (current != null && resistance != null) {
                voltageResult = "${calculateVoltage(current, resistance)} Volts"
                isError = false
            } else {
                voltageResult = "Invalid Input"
                isError = true
            }
        },
        resultText = voltageResult,
        isError = isError
    ) {
        // only the inputs , everything else -> template
        OutlinedTextField(
            value = currentInput,
            onValueChange = { currentInput = it },
            label = { Text("Current (Amps)", color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = resistanceInput,
            onValueChange = { resistanceInput = it },
            label = { Text("Resistance (Ohms)", color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Composable
fun SeriesResistanceCalculator() {
    var resistor1Input by remember { mutableStateOf("") }
    var resistor2Input by remember { mutableStateOf("") }
    var seriesResult by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Series Resistance",
        subtitle = "Calculate Total Resistance (R_total = R1 + R2)",
        onCalculate = {
            val r1 = resistor1Input.toDoubleOrNull()
            val r2 = resistor2Input.toDoubleOrNull()
            if (r1 != null && r2 != null) {
                seriesResult = "R_total = ${calculateSeriesResistance(r1, r2)} Ohms"
                isError = false
            } else {
                seriesResult = "Invalid Input!"
                isError = true
            }
        },
        resultText = seriesResult,
        isError = isError
    ) {
        OutlinedTextField(
            value = resistor1Input,
            onValueChange = { resistor1Input = it },
            label = { Text("R1 (Ohms)", color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = resistor2Input,
            onValueChange = { resistor2Input = it },
            label = { Text("R2 (Ohms)", color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )

    }
}