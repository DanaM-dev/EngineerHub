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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Mechanical helper functions
fun calculateStress(force: Double, area: Double): Double = force / area
fun calculateStrain(changeInLength: Double, originalLength: Double): Double = changeInLength / originalLength

@Composable
fun StressCalculator() {
    var forceValue by remember { mutableStateOf("") }
    var areaValue by remember { mutableStateOf("") }
    var stressResult by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Mechanical Stress",
        subtitle = "Calculate Stress (σ = F / A)",
        onCalculate = {
            val force = forceValue.toDoubleOrNull()
            val area = areaValue.toDoubleOrNull()
             if (force == null || area == null) {
                 isError = true
                 stressResult = "Invalid Input!"
             }
                else if (area == 0.0) {
                    stressResult = "Area cannot be zero!"
                isError = true
                }
                else {
                    stressResult = "Stress: ${calculateStress(force, area)} Pa"
                isError = false
                }
        },
        resultText = stressResult,
        isError = isError
    ) {

        OutlinedTextField(
            value = forceValue,
            onValueChange = { forceValue = it },
            label = { Text("Force F (N)", color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = areaValue,
            onValueChange = { areaValue = it },
            label = { Text("Area A (m²)", color = TextSoftGray) },
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
fun StrainCalculator() {
    var deltaLInput by remember { mutableStateOf("") }
    var initialLInput by remember { mutableStateOf("") }
    var strainResult by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Mechanical Strain",
        subtitle = "Calculate Strain (ε = ΔL / L₀)",
        onCalculate = {
            val deltaL = deltaLInput.toDoubleOrNull()
            val initialL = initialLInput.toDoubleOrNull()
            if (deltaL == null || initialL == null) {
                strainResult = "Invalid Input!"
                isError = true
            } else if (initialL == 0.0) {
                strainResult = "Initial length cannot be zero!"
                isError = true
            } else {
                strainResult = "Strain: ${calculateStrain(deltaL, initialL)} (unitless)"
                isError = false
            }
        },
        resultText = strainResult,
        isError = isError
    ) {

        OutlinedTextField(
            value = deltaLInput,
            onValueChange = { deltaLInput = it },
            label = { Text("Change in Length ΔL (m)",color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = initialLInput,
            onValueChange = { initialLInput = it },
            label = { Text("Initial Length L₀ (m)",color = TextSoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GradientEnd,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )

    }
}