package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt


@Composable
fun EoqCalculator() {
    var demand by remember { mutableStateOf("") }
    var setupCost by remember { mutableStateOf("") }
    var holdingCost by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Economic Order Quantity (EOQ)",
        subtitle = "EOQ = √((2 · D · S) / H)",
        onCalculate = {
            val d = demand.toDoubleOrNull()
            val s = setupCost.toDoubleOrNull()
            val h = holdingCost.toDoubleOrNull()

            if (d != null && s != null && h != null && d >= 0 && s >= 0 && h > 0) {
                val eoq = sqrt((2 * d * s) / h)
                result = "Optimal Order Quantity: ${String.format("%.0f", eoq)} units"
                isError = false
            } else {
                result = "Invalid Input (H > 0)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = demand, onValueChange = { demand = it }, label = "Demand D (units)", modifier = Modifier.weight(1f))
            EngineerTextField(value = setupCost, onValueChange = { setupCost = it }, label = "Order Cost S ($)", modifier = Modifier.weight(1f))
            EngineerTextField(value = holdingCost, onValueChange = { holdingCost = it }, label = "Holding H ($)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun OeeCalculator() {
    var availabilityPercent by remember { mutableStateOf("") }
    var performancePercent by remember { mutableStateOf("") }
    var qualityPercent by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Overall Equipment Effectiveness",
        subtitle = "OEE = Availability × Performance × Quality",
        onCalculate = {
            val a = availabilityPercent.toDoubleOrNull()
            val p = performancePercent.toDoubleOrNull()
            val q = qualityPercent.toDoubleOrNull()

            if (a != null && p != null && q != null && a in 0.0..100.0 && p in 0.0..100.0 && q in 0.0..100.0) {
                val oee = (a / 100.0) * (p / 100.0) * (q / 100.0) * 100.0
                result = "OEE Rate: ${String.format("%.2f", oee)}%"
                isError = false
            } else {
                result = "Enter percentages (0 - 100)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = availabilityPercent, onValueChange = { availabilityPercent = it }, label = "Availability (%)", modifier = Modifier.weight(1f))
            EngineerTextField(value = performancePercent, onValueChange = { performancePercent = it }, label = "Performance (%)", modifier = Modifier.weight(1f))
            EngineerTextField(value = qualityPercent, onValueChange = { qualityPercent = it }, label = "Quality (%)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun TaktTimeCalculator() {
    var availableTimeSec by remember { mutableStateOf("") }
    var customerDemand by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Takt Time Calculator",
        subtitle = "T_t = Net Available Time / Customer Demand",
        onCalculate = {
            val time = availableTimeSec.toDoubleOrNull()
            val demand = customerDemand.toDoubleOrNull()

            if (time != null && demand != null && time > 0 && demand > 0) {
                val taktSec = time / demand
                val taktMins = taktSec / 60.0
                result = "Takt Time: ${String.format("%.2f", taktSec)} sec/unit\n(${String.format("%.2f", taktMins)} min/unit)"
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
            EngineerTextField(value = availableTimeSec, onValueChange = { availableTimeSec = it }, label = "Avail. Time (sec)", modifier = Modifier.weight(1f))
            EngineerTextField(value = customerDemand, onValueChange = { customerDemand = it }, label = "Demand (units)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun BreakEvenCalculator() {
    var fixedCosts by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf("") }
    var variableCostPerUnit by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Break-Even Analysis",
        subtitle = "Q = Fixed Costs / (Price - Variable Cost)",
        onCalculate = {
            val fc = fixedCosts.toDoubleOrNull()
            val p = pricePerUnit.toDoubleOrNull()
            val vc = variableCostPerUnit.toDoubleOrNull()

            if (fc != null && p != null && vc != null && fc >= 0 && p > vc) {
                val breakEvenUnits = fc / (p - vc)
                val breakEvenRevenue = breakEvenUnits * p
                result = "Break-Even Point: ${String.format("%.0f", breakEvenUnits)} units\n(Revenue: $${String.format("%.2f", breakEvenRevenue)})"
                isError = false
            } else {
                result = "Invalid Input (Price must be > Variable Cost)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = fixedCosts, onValueChange = { fixedCosts = it }, label = "Fixed Costs ($)", modifier = Modifier.weight(1f))
            EngineerTextField(value = pricePerUnit, onValueChange = { pricePerUnit = it }, label = "Price ($)", modifier = Modifier.weight(1f))
            EngineerTextField(value = variableCostPerUnit, onValueChange = { variableCostPerUnit = it }, label = "Var Cost ($)", modifier = Modifier.weight(1f))
        }
    }
}