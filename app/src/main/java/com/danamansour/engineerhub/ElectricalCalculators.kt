package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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
                voltageResult = "${current * resistance} Volts"
                isError = false
            } else {
                voltageResult = "Invalid Input"
                isError = true
            }
        },
        resultText = voltageResult,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = currentInput, onValueChange = { currentInput = it }, label = "Current (A)")
            EngineerTextField(value = resistanceInput, onValueChange = { resistanceInput = it }, label = "Resistance (Ω)")
        }
    }
}


@Composable
fun SeriesComponentCalculator() {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Series R & C",
        subtitle = "Calculates Series Resistance OR Capacitance",
        onCalculate = {
            val val1 = input1.toDoubleOrNull()
            val val2 = input2.toDoubleOrNull()
            if (val1 != null && val2 != null && val1 != 0.0 && val2 != 0.0) {
                val rSeries = val1 + val2
                val cSeries = 1 / ((1 / val1) + (1 / val2))
                result = "R_eq: ${String.format("%.2f", rSeries)} Ω\nC_eq: ${String.format("%.2f", cSeries)} F"
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
            EngineerTextField(value = input1, onValueChange = { input1 = it }, label = "Component 1")
            EngineerTextField(value = input2, onValueChange = { input2 = it }, label = "Component 2")
        }
    }
}

@Composable
fun ParallelComponentCalculator() {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Parallel R & C",
        subtitle = "Calculates Parallel Resistance OR Capacitance",
        onCalculate = {
            val val1 = input1.toDoubleOrNull()
            val val2 = input2.toDoubleOrNull()
            if (val1 != null && val2 != null && val1 != 0.0 && val2 != 0.0) {
                val rParallel = 1 / ((1 / val1) + (1 / val2))
                val cParallel = val1 + val2
                result = "R_eq: ${String.format("%.2f", rParallel)} Ω\nC_eq: ${String.format("%.2f", cParallel)} F"
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
            EngineerTextField(value = input1, onValueChange = { input1 = it }, label = "Component 1")
            EngineerTextField(value = input2, onValueChange = { input2 = it }, label = "Component 2")
        }
    }
}


@Composable
fun VoltageDividerCalculator() {
    var vin by remember { mutableStateOf("") }
    var r1 by remember { mutableStateOf("") }
    var r2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Voltage Divider",
        subtitle = "V_out = V_in * (R2 / (R1 + R2))",
        onCalculate = {
            val v = vin.toDoubleOrNull()
            val res1 = r1.toDoubleOrNull()
            val res2 = r2.toDoubleOrNull()
            if (v != null && res1 != null && res2 != null && (res1 + res2) != 0.0) {
                result = "V_out: ${String.format("%.2f", v * (res2 / (res1 + res2)))} V"
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
            EngineerTextField(value = vin, onValueChange = { vin = it }, label = "V_in")
            EngineerTextField(value = r1, onValueChange = { r1 = it }, label = "R1")
            EngineerTextField(value = r2, onValueChange = { r2 = it }, label = "R2")
        }
    }
}

@Composable
fun TimeConstantCalculator() {
    var r by remember { mutableStateOf("") }
    var cOrL by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Time Constant",
        subtitle = "RC (Capacitor) or RL (Inductor)",
        onCalculate = {
            val res = r.toDoubleOrNull()
            val cl = cOrL.toDoubleOrNull()
            if (res != null && cl != null && res != 0.0) {
                result = "RC Time: ${String.format("%.4f", res * cl)} s\nRL Time: ${String.format("%.4f", cl / res)} s"
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
            EngineerTextField(value = r, onValueChange = { r = it }, label = "Resistance (Ω)")
            EngineerTextField(value = cOrL, onValueChange = { cOrL = it }, label = "Cap (F) / Ind (H)")
        }
    }
}

@Composable
fun ResonantFrequencyCalculator() {
    var l by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "RLC Resonant Frequency",
        subtitle = "f0 = 1 / (2π√(LC))",
        onCalculate = {
            val ind = l.toDoubleOrNull()
            val cap = c.toDoubleOrNull()
            if (ind != null && cap != null && ind > 0 && cap > 0) {
                result = "Resonant Freq: ${String.format("%.2f", 1 / (2 * PI * sqrt(ind * cap)))} Hz"
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
            EngineerTextField(value = l, onValueChange = { l = it }, label = "Inductance (H)")
            EngineerTextField(value = c, onValueChange = { c = it }, label = "Capacitance (F)")
        }
    }
}

@Composable
fun ResistorColorCodeCalculator() {
    var band1 by remember { mutableStateOf("") }
    var band2 by remember { mutableStateOf("") }
    var mult by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val colorMap = mapOf("black" to 0, "brown" to 1, "red" to 2, "orange" to 3, "yellow" to 4, "green" to 5, "blue" to 6, "violet" to 7, "gray" to 8, "white" to 9)

    EngineerCalculatorTemplate(
        title = "Resistor Decoder",
        subtitle = "4-Band (e.g., Red, Red, Brown)",
        onCalculate = {
            val d1 = colorMap[band1.lowercase().trim()]
            val d2 = colorMap[band2.lowercase().trim()]
            val m = colorMap[mult.lowercase().trim()]
            if (d1 != null && d2 != null && m != null) {
                val resistance = ((d1 * 10) + d2) * 10.0.pow(m)
                result = "Resistance: ${resistance.toLong()} Ω"
                isError = false
            } else {
                result = "Invalid Color Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = band1, onValueChange = { band1 = it }, label = "Band 1", isTextOnly = true)
            EngineerTextField(value = band2, onValueChange = { band2 = it }, label = "Band 2", isTextOnly = true)
            EngineerTextField(value = mult, onValueChange = { mult = it }, label = "Multiplier", isTextOnly = true)
        }
    }
}


@Composable
fun ACPowerCalculator() {
    var v by remember { mutableStateOf("") }
    var i by remember { mutableStateOf("") }
    var phase by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "AC Power Factor",
        subtitle = "Calculates P, Q, and S",
        onCalculate = {
            val volts = v.toDoubleOrNull()
            val amps = i.toDoubleOrNull()
            val angle = phase.toDoubleOrNull()
            if (volts != null && amps != null && angle != null) {
                val radians = Math.toRadians(angle)
                val s = volts * amps
                result = "Apparent (S): ${String.format("%.2f", s)} VA\nReal (P): ${String.format("%.2f", s * cos(radians))} W\nReactive (Q): ${String.format("%.2f", s * sin(radians))} VAR"
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
            EngineerTextField(value = v, onValueChange = { v = it }, label = "V (rms)")
            EngineerTextField(value = i, onValueChange = { i = it }, label = "I (rms)")
            EngineerTextField(value = phase, onValueChange = { phase = it }, label = "Phase (°)")
        }
    }
}

@Composable
fun OpAmpGainCalculator() {
    var rf by remember { mutableStateOf("") }
    var rin by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Op-Amp Gain",
        subtitle = "Inverting and Non-Inverting",
        onCalculate = {
            val f = rf.toDoubleOrNull()
            val inputR = rin.toDoubleOrNull()
            if (f != null && inputR != null && inputR != 0.0) {
                result = "Inverting Gain: ${String.format("%.2f", -(f / inputR))}\nNon-Inverting: ${String.format("%.2f", 1 + (f / inputR))}"
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
            EngineerTextField(value = rf, onValueChange = { rf = it }, label = "R_feedback")
            EngineerTextField(value = rin, onValueChange = { rin = it }, label = "R_in")
        }
    }
}

@Composable
fun PCBTraceWidthCalculator() {
    var current by remember { mutableStateOf("") }
    var tempRise by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "PCB Trace Width (IPC-2221)",
        subtitle = "Outer Layer (1 oz/ft² copper)",
        onCalculate = {
            val i = current.toDoubleOrNull()
            val dt = tempRise.toDoubleOrNull()
            if (i != null && dt != null && dt > 0) {
                val areaMils = (i / (0.048 * dt.pow(0.44))).pow(1 / 0.725)
                val widthMm = (areaMils / 1.378) * 0.0254
                result = "Required Width: ${String.format("%.2f", widthMm)} mm"
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
            EngineerTextField(value = current, onValueChange = { current = it }, label = "Current (A)")
            EngineerTextField(value = tempRise, onValueChange = { tempRise = it }, label = "Temp Rise (°C)")
        }
    }
}