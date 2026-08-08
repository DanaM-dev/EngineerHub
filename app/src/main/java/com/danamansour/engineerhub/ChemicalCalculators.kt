package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.exp
import kotlin.math.log10
import kotlin.math.pow

@Composable
fun MolarityCalculator() {
    var moles by remember { mutableStateOf("") }
    var volumeLiters by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Molarity & Concentration",
        subtitle = "M = n / V",
        onCalculate = {
            val n = moles.toDoubleOrNull()
            val v = volumeLiters.toDoubleOrNull()
            if (n != null && v != null && v > 0) {
                val molarity = n / v
                result = "Molarity (M): ${String.format("%.4f", molarity)} mol/L"
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
            EngineerTextField(value = moles, onValueChange = { moles = it }, label = "Moles n (mol)", modifier = Modifier.weight(1f))
            EngineerTextField(value = volumeLiters, onValueChange = { volumeLiters = it }, label = "Volume V (L)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DilutionCalculator() {
    var c1 by remember { mutableStateOf("") }
    var v1 by remember { mutableStateOf("") }
    var v2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Dilution Equation",
        subtitle = "C₁ · V₁ = C₂ · V₂",
        onCalculate = {
            val conc1 = c1.toDoubleOrNull()
            val vol1 = v1.toDoubleOrNull()
            val vol2 = v2.toDoubleOrNull()
            if (conc1 != null && vol1 != null && vol2 != null && vol2 > 0) {
                val conc2 = (conc1 * vol1) / vol2
                result = "Final Conc (C₂): ${String.format("%.4f", conc2)} M"
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
            EngineerTextField(value = c1, onValueChange = { c1 = it }, label = "C₁ (Initial)", modifier = Modifier.weight(1f))
            EngineerTextField(value = v1, onValueChange = { v1 = it }, label = "V₁ (Initial)", modifier = Modifier.weight(1f))
            EngineerTextField(value = v2, onValueChange = { v2 = it }, label = "V₂ (Final)", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PhCalculator() {
    var hConcentration by remember { mutableStateOf("") }
    var phInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "pH & [H⁺] Concentration",
        subtitle = "pH = -log₁₀[H⁺]  |  [H⁺] = 10^(-pH)",
        onCalculate = {
            val h = hConcentration.toDoubleOrNull()
            val ph = phInput.toDoubleOrNull()

            if (h != null && h > 0) {
                val calculatedPh = -log10(h)
                result = "pH: ${String.format("%.2f", calculatedPh)}"
                isError = false
            } else if (ph != null && ph in 0.0..14.0) {
                val calculatedH = 10.0.pow(-ph)
                result = "[H⁺]: ${String.format("%.6e", calculatedH)} M"
                isError = false
            } else {
                result = "Enter valid [H⁺] OR pH (0-14)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EngineerTextField(value = hConcentration, onValueChange = { hConcentration = it }, label = "[H⁺] (mol/L)", modifier = Modifier.weight(1f))
            EngineerTextField(value = phInput, onValueChange = { phInput = it }, label = "pH Value", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun ArrheniusCalculator() {
    var preExpA by remember { mutableStateOf("") }
    var actEnergyEa by remember { mutableStateOf("") }
    var tempK by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val R_GAS = 8.31446 // Renamed to avoid R class shadowing

    EngineerCalculatorTemplate(
        title = "Arrhenius Reaction Rate",
        subtitle = "k = A · e^(-Eₐ / (R · T))",
        onCalculate = {
            val a = preExpA.toDoubleOrNull()
            val ea = actEnergyEa.toDoubleOrNull()
            val t = tempK.toDoubleOrNull()

            if (a != null && ea != null && t != null && t > 0) {
                val rateK = a * exp(-ea / (R_GAS * t))
                result = "Rate Constant (k): ${String.format("%.6e", rateK)}"
                isError = false
            } else {
                result = "Invalid Input (T > 0 K)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EngineerTextField(value = preExpA, onValueChange = { preExpA = it }, label = "Factor A", modifier = Modifier.weight(1f))
            EngineerTextField(value = actEnergyEa, onValueChange = { actEnergyEa = it }, label = "Eₐ (J/mol)", modifier = Modifier.weight(1f))
            EngineerTextField(value = tempK, onValueChange = { tempK = it }, label = "Temp T (K)", modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun StoichiometryCalculator() {
    var massReactant by remember { mutableStateOf("") }
    var molarMassReactant by remember { mutableStateOf("") }
    var molarMassProduct by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Stoichiometric Mass Balance",
        subtitle = "Calculates Theoretical Product Yield",
        onCalculate = {
            val mReact = massReactant.toDoubleOrNull()
            val mwReact = molarMassReactant.toDoubleOrNull()
            val mwProd = molarMassProduct.toDoubleOrNull()

            if (mReact != null && mwReact != null && mwProd != null && mwReact > 0 && mwProd > 0) {
                val molesReactant = mReact / mwReact
                val theoreticalMassProduct = molesReactant * mwProd
                result = "Reactant Moles: ${String.format("%.3f", molesReactant)} mol\nTheoretical Product: ${String.format("%.2f", theoreticalMassProduct)} g"
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
            EngineerTextField(value = massReactant, onValueChange = { massReactant = it }, label = "Reactant Mass (g)", modifier = Modifier.weight(1f))
            EngineerTextField(value = molarMassReactant, onValueChange = { molarMassReactant = it }, label = "MW Reactant (g/mol)", modifier = Modifier.weight(1f))
            EngineerTextField(value = molarMassProduct, onValueChange = { molarMassProduct = it }, label = "MW Product (g/mol)", modifier = Modifier.weight(1f))
        }
    }
}