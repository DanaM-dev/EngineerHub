package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ChemicalScreen() {
    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Chemical Engineering",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Molarity & Concentration",
            description = "Calculate Molarity (M = n / V)",
            icon = Icons.Outlined.Science,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            MolarityCalculator()
        }


        ExpandableCalculatorCard(
            title = "Dilution Equation",
            description = "Calculate Final Concentration (C₁ · V₁ = C₂ · V₂)",
            icon = Icons.Outlined.Opacity,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            DilutionCalculator()
        }


        ExpandableCalculatorCard(
            title = "pH & [H⁺] Concentration",
            description = "pH = -log₁₀[H⁺] or [H⁺] = 10^(-pH)",
            icon = Icons.Outlined.WaterDrop,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            PhCalculator()
        }


        ExpandableCalculatorCard(
            title = "Arrhenius Reaction Rate",
            description = "Calculate Rate Constant (k = A · e^(-Eₐ / RT))",
            icon = Icons.Outlined.LocalFireDepartment,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            ArrheniusCalculator()
        }


        ExpandableCalculatorCard(
            title = "Stoichiometric Mass Balance",
            description = "Calculates Theoretical Product Yield",
            icon = Icons.Outlined.Balance,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            StoichiometryCalculator()
        }
    }
}