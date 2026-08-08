package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sin



@Composable
fun RebarWeightCalculator() {
    var diameterMm by remember { mutableStateOf("") }
    var lengthMeters by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Rebar Weight & Length",
        subtitle = "W = (d² · L) / 162",
        onCalculate = {
            val d = diameterMm.toDoubleOrNull()
            val l = lengthMeters.toDoubleOrNull()
            if (d != null && l != null && d > 0 && l > 0) {
                val weightKg = (d.pow(2) * l) / 162.0
                val weightTons = weightKg / 1000.0
                result = "Total Weight: ${String.format("%.2f", weightKg)} kg\n(${String.format("%.3f", weightTons)} Metric Tons)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EngineerTextField(value = diameterMm, onValueChange = { diameterMm = it }, label = "Diameter d (mm)", modifier = Modifier.weight(1f))
            EngineerTextField(value = lengthMeters, onValueChange = { lengthMeters = it }, label = "Length L (m)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ConcreteVolumeCalculator() {
    var lengthM by remember { mutableStateOf("") }
    var widthM by remember { mutableStateOf("") }
    var depthM by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Concrete Volume Estimator",
        subtitle = "V = Length · Width · Depth",
        onCalculate = {
            val l = lengthM.toDoubleOrNull()
            val w = widthM.toDoubleOrNull()
            val d = depthM.toDoubleOrNull()
            if (l != null && w != null && d != null && l > 0 && w > 0 && d > 0) {
                val volumeM3 = l * w * d
                val volumeYards3 = volumeM3 * 1.30795
                result = "Volume: ${String.format("%.2f", volumeM3)} m³\n(${String.format("%.2f", volumeYards3)} yd³)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = lengthM, onValueChange = { lengthM = it }, label = "Length (m)", modifier = Modifier.weight(1f))
            EngineerTextField(value = widthM, onValueChange = { widthM = it }, label = "Width (m)", modifier = Modifier.weight(1f))
            EngineerTextField(value = depthM, onValueChange = { depthM = it }, label = "Depth (m)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ManningsFlowCalculator() {
    var manningN by remember { mutableStateOf("") }
    var hydraulicRadius by remember { mutableStateOf("") }
    var slope by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Manning's Hydraulic Flow",
        subtitle = "V = (1 / n) · R_h^(2/3) · S^(1/2)",
        onCalculate = {
            val n = manningN.toDoubleOrNull()
            val rh = hydraulicRadius.toDoubleOrNull()
            val s = slope.toDoubleOrNull()

            if (n != null && rh != null && s != null && n > 0 && rh > 0 && s >= 0) {
                val velocity = (1.0 / n) * rh.pow(2.0 / 3.0) * s.pow(0.5)
                result = "Flow Velocity (V): ${String.format("%.3f", velocity)} m/s"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = manningN, onValueChange = { manningN = it }, label = "Manning n", modifier = Modifier.weight(1f))
            EngineerTextField(value = hydraulicRadius, onValueChange = { hydraulicRadius = it }, label = "R_h (m)", modifier = Modifier.weight(1f))
            EngineerTextField(value = slope, onValueChange = { slope = it }, label = "Slope S (m/m)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun EarthPressureCalculator() {
    var frictionAngleDeg by remember { mutableStateOf("") }
    var unitWeight by remember { mutableStateOf("") }
    var wallHeight by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Rankine Earth Pressure",
        subtitle = "K_a = tan²(45° - ϕ/2)  |  K_p = tan²(45° + ϕ/2)",
        onCalculate = {
            val phi = frictionAngleDeg.toDoubleOrNull()
            val gamma = unitWeight.toDoubleOrNull()
            val h = wallHeight.toDoubleOrNull()

            if (phi != null && gamma != null && h != null && phi in 0.0..89.0 && gamma > 0 && h > 0) {
                val phiRad = Math.toRadians(phi)
                val ka = (1 - sin(phiRad)) / (1 + sin(phiRad))
                val kp = (1 + sin(phiRad)) / (1 - sin(phiRad))

                val activeForce = 0.5 * ka * gamma * h.pow(2)
                val passiveForce = 0.5 * kp * gamma * h.pow(2)

                result = "K_a: ${String.format("%.3f", ka)} | K_p: ${String.format("%.3f", kp)}\nActive Thrust (P_a): ${String.format("%.2f", activeForce)} kN/m\nPassive Resistance (P_p): ${String.format("%.2f", passiveForce)} kN/m"
                isError = false
            } else {
                result = "Invalid Input (0° ≤ ϕ < 90°)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = frictionAngleDeg, onValueChange = { frictionAngleDeg = it }, label = "Friction ϕ (°)", modifier = Modifier.weight(1f))
            EngineerTextField(value = unitWeight, onValueChange = { unitWeight = it }, label = "Soil γ (kN/m³)", modifier = Modifier.weight(1f))
            EngineerTextField(value = wallHeight, onValueChange = { wallHeight = it }, label = "Height H (m)", modifier = Modifier.weight(1f))
        }
    }
}