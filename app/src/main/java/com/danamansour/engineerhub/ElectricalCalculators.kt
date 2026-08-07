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
import androidx.compose.ui.unit.dp

// Electrical helper functions
fun calculateVoltage(current: Double, resistance: Double): Double = current * resistance
fun calculateSeriesResistance(r1: Double, r2: Double): Double = r1 + r2

@Composable
fun VoltageCalculator() {
    var currentInput by remember { mutableStateOf("") }
    var resistanceInput by remember { mutableStateOf("") }
    var voltageResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Ohm's Law Calculator")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = currentInput,
                    onValueChange = { currentInput = it },
                    label = { Text("Enter current (Amps)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = resistanceInput,
                    onValueChange = { resistanceInput = it },
                    label = { Text("Enter resistance (Ohms)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val current = currentInput.toDoubleOrNull()
                        val resistance = resistanceInput.toDoubleOrNull()
                        voltageResult = if (current != null && resistance != null) {
                            "Voltage: ${calculateVoltage(current, resistance)} Volts"
                        } else {
                            "Invalid Input!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Calculate Voltage")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = voltageResult)
            }
        }
    }
}

@Composable
fun SeriesResistanceCalculator() {
    var resistor1Input by remember { mutableStateOf("") }
    var resistor2Input by remember { mutableStateOf("") }
    var seriesResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Series Resistance Calculator")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = resistor1Input,
                    onValueChange = { resistor1Input = it },
                    label = { Text("R1 (Ohms)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = resistor2Input,
                    onValueChange = { resistor2Input = it },
                    label = { Text("R2 (Ohms)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val r1 = resistor1Input.toDoubleOrNull()
                        val r2 = resistor2Input.toDoubleOrNull()
                        seriesResult = if (r1 != null && r2 != null) {
                            "R_total = ${calculateSeriesResistance(r1, r2)} Ohms"
                        } else {
                            "Invalid Input!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Calculate Series Resistance")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = seriesResult)
            }
        }
    }
}