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
fun AerospaceScreen() {
    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Aerospace & Environment",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Aerodynamic Force (Lift / Drag)",
            description = "Calculate aerodynamic lift or drag forces",
            icon = Icons.Outlined.AirplanemodeActive,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            AerodynamicLiftDragCalculator()
        }


        ExpandableCalculatorCard(
            title = "US Standard Atmosphere (ISA)",
            description = "Troposphere model for temperature, pressure & density",
            icon = Icons.Outlined.Public,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            StandardAtmosphereCalculator()
        }


        ExpandableCalculatorCard(
            title = "Rocket Thrust Equation",
            description = "Calculate rocket motor thrust and mass flow",
            icon = Icons.Outlined.RocketLaunch,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            RocketThrustCalculator()
        }


        ExpandableCalculatorCard(
            title = "Air Quality Index (PM2.5)",
            description = "EPA PM2.5 concentration breakpoint mapping",
            icon = Icons.Outlined.Co2,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            AqiCalculator()
        }


        ExpandableCalculatorCard(
            title = "Noise Level & Sound Pressure",
            description = "Calculate acoustic pressure and sound pressure level (dBSPL)",
            icon = Icons.Outlined.VolumeUp,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            NoiseLevelCalculator()
        }
    }
}