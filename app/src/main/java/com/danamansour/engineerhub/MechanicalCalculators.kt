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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Mechanical helper functions
fun calculateStress(force: Double, area: Double): Double = force / area
fun calculateStrain(changeInLength: Double, originalLength: Double): Double = changeInLength / originalLength

@Composable
fun StressCalculator() {
    var forceValue by remember { mutableStateOf("") }
    var areaValue by remember { mutableStateOf("") }
    var stressResult by remember { mutableStateOf("") }

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Stress Calculator (σ = F / A)")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = forceValue,
                onValueChange = { forceValue = it },
                label = { Text("Force F (N)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = areaValue,
                onValueChange = { areaValue = it },
                label = { Text("Area A (m²)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val force = forceValue.toDoubleOrNull()
                    val area = areaValue.toDoubleOrNull()
                    stressResult = when {
                        force == null || area == null -> "Invalid Input!"
                        area == 0.0 -> "Area cannot be zero!"
                        else -> "Stress: ${calculateStress(force, area)} Pa"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Calculate Stress")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stressResult)
        }
    }
}

@Composable
fun StrainCalculator() {
    var deltaLInput by remember { mutableStateOf("") }
    var initialLInput by remember { mutableStateOf("") }
    var strainResult by remember { mutableStateOf("") }

    ElevatedCard(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Strain Calculator (ε = ΔL / L₀)")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = deltaLInput,
                onValueChange = { deltaLInput = it },
                label = { Text("Change in Length ΔL (m)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = initialLInput,
                onValueChange = { initialLInput = it },
                label = { Text("Initial Length L₀ (m)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val deltaL = deltaLInput.toDoubleOrNull()
                    val initialL = initialLInput.toDoubleOrNull()
                    strainResult = when {
                        deltaL == null || initialL == null -> "Invalid Input!"
                        initialL == 0.0 -> "Initial length cannot be zero!"
                        else -> "Strain: ${calculateStrain(deltaL, initialL)} (unitless)"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Calculate Strain")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = strainResult)
        }
    }
}