package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.log10
import kotlin.math.pow


@Composable
fun AerodynamicLiftDragCalculator() {
    var density by remember { mutableStateOf("") }
    var velocity by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var coeff by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Aerodynamic Force (Lift / Drag)",
        subtitle = "F = ½ · ρ · v² · A · C",
        onCalculate = {
            val rho = density.toDoubleOrNull()
            val v = velocity.toDoubleOrNull()
            val a = area.toDoubleOrNull()
            val c = coeff.toDoubleOrNull()

            if (rho != null && v != null && a != null && c != null && rho > 0 && a > 0) {
                val forceN = 0.5 * rho * v.pow(2) * a * c
                val forceKn = forceN / 1000.0
                result = "Force (Lift/Drag): ${String.format("%.2f", forceN)} N\n(${String.format("%.3f", forceKn)} kN)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = density, onValueChange = { density = it }, label = "Density ρ (kg/m³)")
            EngineerTextField(value = velocity, onValueChange = { velocity = it }, label = "Velocity v (m/s)")
            EngineerTextField(value = area, onValueChange = { area = it }, label = "Area A (m²)")
            EngineerTextField(value = coeff, onValueChange = { coeff = it }, label = "Coeff C_L / C_D")
        }
    }
}


@Composable
fun StandardAtmosphereCalculator() {
    var altitudeMeters by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "US Standard Atmosphere (ISA)",
        subtitle = "Troposphere Model (0 to 11,000 m)",
        onCalculate = {
            val h = altitudeMeters.toDoubleOrNull()
            if (h != null && h in 0.0..11000.0) {
                val tempK = 288.15 - (0.0065 * h)
                val tempC = tempK - 273.15
                val pressurePa = 101325.0 * (1.0 - (0.0065 * h / 288.15)).pow(5.2561)
                val density = pressurePa / (287.058 * tempK)

                result = "Temp: ${String.format("%.1f", tempC)} °C | Press: ${String.format("%.2f", pressurePa / 1000.0)} kPa\nDensity: ${String.format("%.3f", density)} kg/m³"
                isError = false
            } else {
                result = "Altitude must be 0 to 11,000 m"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        EngineerTextField(
            value = altitudeMeters,
            onValueChange = { altitudeMeters = it },
            label = "Altitude h (m)"
        )
    }
}


@Composable
fun RocketThrustCalculator() {
    var mDot by remember { mutableStateOf("") }
    var vExit by remember { mutableStateOf("") }
    var pExit by remember { mutableStateOf("") }
    var pAmb by remember { mutableStateOf("") }
    var areaExit by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Rocket Thrust Equation",
        subtitle = "F = (ṁ · v_e) + (P_e - P_a) · A_e",
        onCalculate = {
            val m = mDot.toDoubleOrNull()
            val ve = vExit.toDoubleOrNull()
            val pe = pExit.toDoubleOrNull() ?: 0.0
            val pa = pAmb.toDoubleOrNull() ?: 0.0
            val ae = areaExit.toDoubleOrNull() ?: 0.0

            if (m != null && ve != null && m >= 0 && ve >= 0) {
                val thrustN = (m * ve) + ((pe - pa) * ae)
                val thrustKn = thrustN / 1000.0
                result = "Thrust (F): ${String.format("%.2f", thrustN)} N\n(${String.format("%.2f", thrustKn)} kN)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = mDot, onValueChange = { mDot = it }, label = "ṁ Mass Flow (kg/s)")
            EngineerTextField(value = vExit, onValueChange = { vExit = it }, label = "v_e Exit Vel (m/s)")
            EngineerTextField(value = pExit, onValueChange = { pExit = it }, label = "P_e Exit Press (Pa)")
            EngineerTextField(value = pAmb, onValueChange = { pAmb = it }, label = "P_a Amb Press (Pa)")
            EngineerTextField(value = areaExit, onValueChange = { areaExit = it }, label = "A_e Nozzle (m²)")
        }
    }
}

@Composable
fun AqiCalculator() {
    var pm25Conc by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Air Quality Index (PM2.5)",
        subtitle = "EPA PM2.5 Breakpoint Mapping",
        onCalculate = {
            val c = pm25Conc.toDoubleOrNull()
            if (c != null && c >= 0.0) {
                val (aqi, category) = when {
                    c <= 12.0 -> calculateAqiSegment(c, 0.0, 12.0, 0, 50) to "Good (Green)"
                    c <= 35.4 -> calculateAqiSegment(c, 12.1, 35.4, 51, 100) to "Moderate (Yellow)"
                    c <= 55.4 -> calculateAqiSegment(c, 35.5, 55.4, 101, 150) to "Unhealthy for Sensitive Groups"
                    c <= 150.4 -> calculateAqiSegment(c, 55.5, 150.4, 151, 200) to "Unhealthy (Red)"
                    c <= 250.4 -> calculateAqiSegment(c, 150.5, 250.4, 201, 300) to "Very Unhealthy (Purple)"
                    else -> calculateAqiSegment(c, 250.5, 500.4, 301, 500) to "Hazardous (Maroon)"
                }
                result = "AQI: $aqi\nCategory: $category"
                isError = false
            } else {
                result = "Invalid Concentration"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        EngineerTextField(
            value = pm25Conc,
            onValueChange = { pm25Conc = it },
            label = "PM2.5 Concentration (μg/m³)"
        )
    }
}

private fun calculateAqiSegment(c: Double, cLow: Double, cHigh: Double, iLow: Int, iHigh: Int): Int {
    return (((iHigh - iLow) / (cHigh - cLow)) * (c - cLow) + iLow).toInt()
}

@Composable
fun NoiseLevelCalculator() {
    var pressurePascals by remember { mutableStateOf("") }
    var soundDbm by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val pRef = 2e-5 // 20 micropascals standard reference

    EngineerCalculatorTemplate(
        title = "Noise Level & Sound Pressure",
        subtitle = "SPL (dB) = 20 · log₁₀(P / P_ref)",
        onCalculate = {
            val p = pressurePascals.toDoubleOrNull()
            val db = soundDbm.toDoubleOrNull()

            if (p != null && p > 0) {
                val spl = 20 * log10(p / pRef)
                result = "Sound Pressure Level: ${String.format("%.2f", spl)} dBSPL"
                isError = false
            } else if (db != null) {
                val pCalc = pRef * 10.0.pow(db / 20.0)
                result = "Acoustic Pressure: ${String.format("%.6f", pCalc)} Pa"
                isError = false
            } else {
                result = "Enter Pressure (Pa) OR dBSPL"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = pressurePascals, onValueChange = { pressurePascals = it }, label = "Pressure P (Pa)")
            EngineerTextField(value = soundDbm, onValueChange = { soundDbm = it }, label = "Sound Level (dBSPL)")
        }
    }
}