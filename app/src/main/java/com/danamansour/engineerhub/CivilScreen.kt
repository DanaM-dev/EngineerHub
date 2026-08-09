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
fun CivilScreen() {

    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Civil Engineering",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Rebar Weight & Length",
            description = "Calculate steel rebar mass (W = d²L / 162)",
            icon = Icons.Outlined.Architecture,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            RebarWeightCalculator()
        }

        ExpandableCalculatorCard(
            title = "Concrete Volume Estimator",
            description = "Calculate required concrete volume (V = L · W · D)",
            icon = Icons.Outlined.SquareFoot,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            ConcreteVolumeCalculator()
        }


        ExpandableCalculatorCard(
            title = "Manning's Hydraulic Flow",
            description = "Calculate open channel flow velocity",
            icon = Icons.Outlined.WaterDrop,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            ManningsFlowCalculator()
        }


        ExpandableCalculatorCard(
            title = "Rankine Earth Pressure",
            description = "Calculate active and passive soil thrust forces",
            icon = Icons.Outlined.Terrain,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            EarthPressureCalculator()
        }
    }
}