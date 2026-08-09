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
fun ThermodynamicsScreen() {

    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Thermodynamics & Fluids",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Ideal Gas Law",
            description = "Calculate Pressure (P = nRT / V)",
            icon = Icons.Outlined.Air,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            IdealGasLawCalculator()
        }


        ExpandableCalculatorCard(
            title = "Heat Conduction",
            description = "Calculate Heat Transfer Rate (Q = kA·ΔT / d)",
            icon = Icons.Outlined.DeviceThermostat,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            ConductionHeatCalculator()
        }


        ExpandableCalculatorCard(
            title = "Heat Convection",
            description = "Calculate Heat Transfer Rate (Q = hA·ΔT)",
            icon = Icons.Outlined.LocalFireDepartment,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            ConvectionHeatCalculator()
        }


        ExpandableCalculatorCard(
            title = "Carnot Efficiency",
            description = "Calculate Max Cycle Efficiency (η = 1 - T_C / T_H)",
            icon = Icons.Outlined.Loop,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            CarnotEfficiencyCalculator()
        }


        ExpandableCalculatorCard(
            title = "Reynolds Number",
            description = "Determine flow regime (Re = ρvD / μ)",
            icon = Icons.Outlined.Waves,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            ReynoldsNumberCalculator()
        }


        ExpandableCalculatorCard(
            title = "Bernoulli Pressure Drop",
            description = "Calculate fluid pressure changes (ΔP)",
            icon = Icons.Outlined.Compress,
            expanded = expandedIndex == 5,
            onExpandChanged = { expandedIndex = if (it) 5 else -1 }
        ) {
            BernoulliPressureCalculator()
        }


        ExpandableCalculatorCard(
            title = "Flow Rate & Velocity",
            description = "Calculate volumetric flow rate (Q = Av)",
            icon = Icons.Outlined.Water,
            expanded = expandedIndex == 6,
            onExpandChanged = { expandedIndex = if (it) 6 else -1 }
        ) {
            FlowRateCalculator()
        }
    }
}