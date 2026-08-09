package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.pow


@Composable
fun StressCalculator() {
    var force by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Tensile Stress",
        subtitle = "σ = F / A",
        onCalculate = {
            val f = force.toDoubleOrNull()
            val a = area.toDoubleOrNull()
            if (f != null && a != null && a > 0) {
                result = "Stress: ${String.format("%.2f", f / a)} Pa (or N/m²)"
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
            EngineerTextField(value = force, onValueChange = { force = it }, label = "Force (N)")
            EngineerTextField(value = area, onValueChange = { area = it }, label = "Area (m²)")
        }
    }
}


@Composable
fun StrainCalculator() {
    var deltaL by remember { mutableStateOf("") }
    var originalL by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Strain",
        subtitle = "ε = ΔL / L",
        onCalculate = {
            val dl = deltaL.toDoubleOrNull()
            val l = originalL.toDoubleOrNull()
            if (dl != null && l != null && l > 0) {
                val strainVal = dl / l
                result = "Strain: ${String.format("%.6f", strainVal)} (Unitless)\nPercent: ${String.format("%.4f", strainVal * 100)}%"
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
            EngineerTextField(value = deltaL, onValueChange = { deltaL = it }, label = "ΔL (Elongation)")
            EngineerTextField(value = originalL, onValueChange = { originalL = it }, label = "L₀ (Original Length)")
        }
    }
}

@Composable
fun YoungsModulusCalculator() {
    var stress by remember { mutableStateOf("") }
    var strain by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Young's Modulus (E)",
        subtitle = "E = σ / ε (Hooke's Law)",
        onCalculate = {
            val s = stress.toDoubleOrNull()
            val e = strain.toDoubleOrNull()
            if (s != null && e != null && e > 0) {
                val gpa = (s / e) / 1e9
                result = "Modulus (E): ${String.format("%.2f", s / e)} Pa\n(${String.format("%.3f", gpa)} GPa)"
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
            EngineerTextField(value = stress, onValueChange = { stress = it }, label = "Stress σ (Pa)")
            EngineerTextField(value = strain, onValueChange = { strain = it }, label = "Strain ε (Unitless)")
        }
    }
}

@Composable
fun BendingStressCalculator() {
    var moment by remember { mutableStateOf("") }
    var distanceY by remember { mutableStateOf("") }
    var inertiaI by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Beam Bending Stress",
        subtitle = "σ = (M · y) / I",
        onCalculate = {
            val m = moment.toDoubleOrNull()
            val y = distanceY.toDoubleOrNull()
            val i = inertiaI.toDoubleOrNull()
            if (m != null && y != null && i != null && i > 0) {
                result = "Bending Stress: ${String.format("%.2f", (m * y) / i)} Pa"
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
            EngineerTextField(value = moment, onValueChange = { moment = it }, label = "M (N·m)")
            EngineerTextField(value = distanceY, onValueChange = { distanceY = it }, label = "y (m)")
            EngineerTextField(value = inertiaI, onValueChange = { inertiaI = it }, label = "I (m⁴)")
        }
    }
}

@Composable
fun TorsionShearStressCalculator() {
    var torque by remember { mutableStateOf("") }
    var radius by remember { mutableStateOf("") }
    var polarJ by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Torsion / Shaft Shear Stress",
        subtitle = "τ = (T · r) / J",
        onCalculate = {
            val t = torque.toDoubleOrNull()
            val r = radius.toDoubleOrNull()
            val j = polarJ.toDoubleOrNull()
            if (t != null && r != null && j != null && j > 0) {
                result = "Shear Stress (τ): ${String.format("%.2f", (t * r) / j)} Pa"
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
            EngineerTextField(value = torque, onValueChange = { torque = it }, label = "Torque T (N·m)")
            EngineerTextField(value = radius, onValueChange = { radius = it }, label = "Radius r (m)")
            EngineerTextField(value = polarJ, onValueChange = { polarJ = it }, label = "Polar J (m⁴)")
        }
    }
}

@Composable
fun AreaMomentOfInertiaCalculator() {
    var base by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var diameter by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Area Moment of Inertia",
        subtitle = "Rectangular (b·h³/12) OR Circular (π·d⁴/64)",
        onCalculate = {
            val b = base.toDoubleOrNull()
            val h = height.toDoubleOrNull()
            val d = diameter.toDoubleOrNull()

            var resStr = ""
            var valid = false

            if (b != null && h != null && b > 0 && h > 0) {
                val iRect = (b * h.pow(3)) / 12.0
                resStr += "Rectangular I: ${String.format("%.6e", iRect)} m⁴"
                valid = true
            }

            if (d != null && d > 0) {
                val iCircle = (PI * d.pow(4)) / 64.0
                if (valid) resStr += "\n"
                resStr += "Circular I: ${String.format("%.6e", iCircle)} m⁴"
                valid = true
            }

            if (valid) {
                result = resStr
                isError = false
            } else {
                result = "Enter (Base & Height) OR (Diameter)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = base, onValueChange = { base = it }, label = "Base b (m)")
            EngineerTextField(value = height, onValueChange = { height = it }, label = "Height h (m)")
            EngineerTextField(value = diameter, onValueChange = { diameter = it }, label = "Circle Diameter d (m)")
        }
    }
}


@Composable
fun TorqueToPowerCalculator() {
    var torque by remember { mutableStateOf("") }
    var rpm by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Torque to Power",
        subtitle = "P = τ · ω (ω derived from RPM)",
        onCalculate = {
            val t = torque.toDoubleOrNull()
            val revs = rpm.toDoubleOrNull()
            if (t != null && revs != null) {
                val omega = (2 * PI * revs) / 60.0
                val powerWatts = t * omega
                val hp = powerWatts / 745.7
                result = "Power: ${String.format("%.2f", powerWatts)} W\n(${String.format("%.2f", hp)} HP)"
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
            EngineerTextField(value = torque, onValueChange = { torque = it }, label = "Torque τ (N·m)")
            EngineerTextField(value = rpm, onValueChange = { rpm = it }, label = "Speed (RPM)")
        }
    }
}

@Composable
fun GearRatioCalculator() {
    var n1 by remember { mutableStateOf("") }
    var n2 by remember { mutableStateOf("") }
    var speedIn by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Gear Ratio & Speed",
        subtitle = "i = N1 / N2 = ω1 / ω2",
        onCalculate = {
            val teeth1 = n1.toDoubleOrNull()
            val teeth2 = n2.toDoubleOrNull()
            val sIn = speedIn.toDoubleOrNull() ?: 0.0

            if (teeth1 != null && teeth2 != null && teeth2 > 0) {
                val ratio = teeth1 / teeth2
                var resStr = "Gear Ratio (i): ${String.format("%.2f", ratio)}:1"
                if (sIn > 0) {
                    val sOut = sIn / ratio
                    resStr += "\nOutput Speed: ${String.format("%.2f", sOut)} RPM"
                }
                result = resStr
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
            EngineerTextField(value = n1, onValueChange = { n1 = it }, label = "N1 (Driver)")
            EngineerTextField(value = n2, onValueChange = { n2 = it }, label = "N2 (Driven)")
            EngineerTextField(value = speedIn, onValueChange = { speedIn = it }, label = "In Speed (RPM)")
        }
    }
}

@Composable
fun ThermalExpansionCalculator() {
    var alpha by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var tempChange by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Thermal Expansion",
        subtitle = "ΔL = α · L₀ · ΔT",
        onCalculate = {
            val a = alpha.toDoubleOrNull()
            val l0 = length.toDoubleOrNull()
            val dt = tempChange.toDoubleOrNull()
            if (a != null && l0 != null && dt != null) {
                val deltaL = a * l0 * dt
                result = "Expansion (ΔL): ${String.format("%.6f", deltaL)} m\n(${String.format("%.3f", deltaL * 1000)} mm)"
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
            EngineerTextField(value = alpha, onValueChange = { alpha = it }, label = "α (1/°C)")
            EngineerTextField(value = length, onValueChange = { length = it }, label = "L₀ (m)")
            EngineerTextField(value = tempChange, onValueChange = { tempChange = it }, label = "ΔT (°C)")
        }
    }
}