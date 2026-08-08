package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.pow


@Composable
fun IdealGasLawCalculator() {
    var volume by remember { mutableStateOf("") }
    var moles by remember { mutableStateOf("") }
    var tempK by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val R_GAS = 8.31446


    EngineerCalculatorTemplate(
        title = "Ideal Gas Law",
        subtitle = "P = (n · R · T) / V",
        onCalculate = {
            val v = volume.toDoubleOrNull()
            val n = moles.toDoubleOrNull()
            val t = tempK.toDoubleOrNull()
            if (v != null && n != null && t != null && v > 0 && t >= 0) {
                val pressurePa = (n * R_GAS * t) / v
                val kPa = pressurePa / 1000.0
                result = "Pressure (P): ${String.format("%.2f", pressurePa)} Pa\n(${String.format("%.2f", kPa)} kPa)"
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
            EngineerTextField(value = volume, onValueChange = { volume = it }, label = "Volume V (m³)", modifier = Modifier.weight(1f))
            EngineerTextField(value = moles, onValueChange = { moles = it }, label = "Moles n (mol)", modifier = Modifier.weight(1f))
            EngineerTextField(value = tempK, onValueChange = { tempK = it }, label = "Temp T (K)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ConductionHeatCalculator() {
    var kCond by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var deltaT by remember { mutableStateOf("") }
    var thickness by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Heat Conduction",
        subtitle = "Q = k · A · (ΔT / d)",
        onCalculate = {
            val k = kCond.toDoubleOrNull()
            val a = area.toDoubleOrNull()
            val dt = deltaT.toDoubleOrNull()
            val d = thickness.toDoubleOrNull()
            if (k != null && a != null && dt != null && d != null && d > 0) {
                val heatRate = (k * a * dt) / d
                result = "Heat Transfer Rate (Q): ${String.format("%.2f", heatRate)} W"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = kCond, onValueChange = { kCond = it }, label = "k (W/m·K)", modifier = Modifier.weight(1f))
                EngineerTextField(value = area, onValueChange = { area = it }, label = "Area A (m²)", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = deltaT, onValueChange = { deltaT = it }, label = "ΔT (°C or K)", modifier = Modifier.weight(1f))
                EngineerTextField(value = thickness, onValueChange = { thickness = it }, label = "Thickness d (m)", modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
fun ConvectionHeatCalculator() {
    var hCoeff by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var deltaT by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Heat Convection",
        subtitle = "Q = h · A · ΔT",
        onCalculate = {
            val h = hCoeff.toDoubleOrNull()
            val a = area.toDoubleOrNull()
            val dt = deltaT.toDoubleOrNull()
            if (h != null && a != null && dt != null) {
                val heatRate = h * a * dt
                result = "Heat Transfer Rate (Q): ${String.format("%.2f", heatRate)} W"
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
            EngineerTextField(value = hCoeff, onValueChange = { hCoeff = it }, label = "h (W/m²·K)", modifier = Modifier.weight(1f))
            EngineerTextField(value = area, onValueChange = { area = it }, label = "Area A (m²)", modifier = Modifier.weight(1f))
            EngineerTextField(value = deltaT, onValueChange = { deltaT = it }, label = "ΔT (°C or K)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun CarnotEfficiencyCalculator() {
    var tempCold by remember { mutableStateOf("") }
    var tempHot by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Carnot Efficiency",
        subtitle = "η = 1 - (T_C / T_H)",
        onCalculate = {
            val tc = tempCold.toDoubleOrNull()
            val th = tempHot.toDoubleOrNull()
            if (tc != null && th != null && th > 0 && tc >= 0 && tc < th) {
                val efficiency = (1.0 - (tc / th)) * 100.0
                result = "Max Efficiency (η): ${String.format("%.2f", efficiency)}%"
                isError = false
            } else {
                result = "Invalid Input (T_H > T_C > 0)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EngineerTextField(value = tempCold, onValueChange = { tempCold = it }, label = "T_Cold (K)", modifier = Modifier.weight(1f))
            EngineerTextField(value = tempHot, onValueChange = { tempHot = it }, label = "T_Hot (K)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun ReynoldsNumberCalculator() {
    var density by remember { mutableStateOf("") }
    var velocity by remember { mutableStateOf("") }
    var diameter by remember { mutableStateOf("") }
    var viscosity by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Reynolds Number",
        subtitle = "Re = (ρ · v · D) / μ",
        onCalculate = {
            val rho = density.toDoubleOrNull()
            val v = velocity.toDoubleOrNull()
            val d = diameter.toDoubleOrNull()
            val mu = viscosity.toDoubleOrNull()
            if (rho != null && v != null && d != null && mu != null && mu > 0) {
                val re = (rho * v * d) / mu
                val flowType = when {
                    re < 2300 -> "Laminar Flow"
                    re in 2300.0..4000.0 -> "Transient Flow"
                    else -> "Turbulent Flow"
                }
                result = "Re: ${String.format("%.2f", re)}\nFlow Regime: $flowType"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = density, onValueChange = { density = it }, label = "Density ρ (kg/m³)", modifier = Modifier.weight(1f))
                EngineerTextField(value = velocity, onValueChange = { velocity = it }, label = "Velocity v (m/s)", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = diameter, onValueChange = { diameter = it }, label = "Diameter D (m)", modifier = Modifier.weight(1f))
                EngineerTextField(value = viscosity, onValueChange = { viscosity = it }, label = "Viscosity μ (Pa·s)", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun BernoulliPressureCalculator() {
    var density by remember { mutableStateOf("") }
    var v1 by remember { mutableStateOf("") }
    var v2 by remember { mutableStateOf("") }
    var deltaH by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val g = 9.81

    EngineerCalculatorTemplate(
        title = "Bernoulli Pressure Drop",
        subtitle = "ΔP = ½ρ(v₂² - v₁²) + ρg(h₂ - h₁)",
        onCalculate = {
            val rho = density.toDoubleOrNull()
            val vel1 = v1.toDoubleOrNull()
            val vel2 = v2.toDoubleOrNull()
            val dh = deltaH.toDoubleOrNull() ?: 0.0

            if (rho != null && vel1 != null && vel2 != null) {
                val deltaP = (0.5 * rho * (vel2.pow(2) - vel1.pow(2))) + (rho * g * dh)
                result = "Pressure Drop (ΔP): ${String.format("%.2f", deltaP)} Pa\n(${String.format("%.3f", deltaP / 1000.0)} kPa)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = density, onValueChange = { density = it }, label = "Density ρ (kg/m³)", modifier = Modifier.weight(1f))
                EngineerTextField(value = deltaH, onValueChange = { deltaH = it }, label = "Δh (h₂ - h₁) (m)", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EngineerTextField(value = v1, onValueChange = { v1 = it }, label = "v₁ (m/s)", modifier = Modifier.weight(1f))
                EngineerTextField(value = v2, onValueChange = { v2 = it }, label = "v₂ (m/s)", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun FlowRateCalculator() {
    var diameter by remember { mutableStateOf("") }
    var velocity by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Flow Rate & Velocity",
        subtitle = "Q = A · v (where A = π · D² / 4)",
        onCalculate = {
            val d = diameter.toDoubleOrNull()
            val v = velocity.toDoubleOrNull()
            if (d != null && v != null && d > 0) {
                val area = (PI * d.pow(2)) / 4.0
                val q = area * v // m³/s
                val litersPerSec = q * 1000.0
                result = "Flow Rate (Q): ${String.format("%.4f", q)} m³/s\n(${String.format("%.2f", litersPerSec)} L/s)"
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
            EngineerTextField(value = diameter, onValueChange = { diameter = it }, label = "Pipe Diameter D (m)", modifier = Modifier.weight(1f))
            EngineerTextField(value = velocity, onValueChange = { velocity = it }, label = "Velocity v (m/s)", modifier = Modifier.weight(1f))
        }
    }
}