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
fun MechanicalScreen() {
    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Mechanical Engineering",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Tensile Stress",
            description = "Calculate Stress (σ = F / A)",
            icon = Icons.Outlined.Compress,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            StressCalculator()
        }


        ExpandableCalculatorCard(
            title = "Strain",
            description = "Calculate Strain (ε = ΔL / L₀)",
            icon = Icons.Outlined.Straighten,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            StrainCalculator()
        }


        ExpandableCalculatorCard(
            title = "Young's Modulus (E)",
            description = "Hooke's Law (E = σ / ε)",
            icon = Icons.Outlined.Science,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            YoungsModulusCalculator()
        }


        ExpandableCalculatorCard(
            title = "Beam Bending Stress",
            description = "Calculate Bending Stress (σ = M·y / I)",
            icon = Icons.Outlined.ShowChart,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            BendingStressCalculator()
        }


        ExpandableCalculatorCard(
            title = "Torsion / Shaft Shear Stress",
            description = "Calculate Shear Stress (τ = T·r / J)",
            icon = Icons.Outlined.RotateRight,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            TorsionShearStressCalculator()
        }


        ExpandableCalculatorCard(
            title = "Area Moment of Inertia",
            description = "Rectangular or Circular cross-sections",
            icon = Icons.Outlined.CropSquare,
            expanded = expandedIndex == 5,
            onExpandChanged = { expandedIndex = if (it) 5 else -1 }
        ) {
            AreaMomentOfInertiaCalculator()
        }


        ExpandableCalculatorCard(
            title = "Torque to Power",
            description = "Calculate Power from Torque and RPM",
            icon = Icons.Outlined.Speed,
            expanded = expandedIndex == 6,
            onExpandChanged = { expandedIndex = if (it) 6 else -1 }
        ) {
            TorqueToPowerCalculator()
        }


        ExpandableCalculatorCard(
            title = "Gear Ratio & Speed",
            description = "Calculate gear train ratios and output speed",
            icon = Icons.Outlined.Settings,
            expanded = expandedIndex == 7,
            onExpandChanged = { expandedIndex = if (it) 7 else -1 }
        ) {
            GearRatioCalculator()
        }


        ExpandableCalculatorCard(
            title = "Thermal Expansion",
            description = "Calculate linear expansion (ΔL = α·L₀·ΔT)",
            icon = Icons.Outlined.Thermostat,
            expanded = expandedIndex == 8,
            onExpandChanged = { expandedIndex = if (it) 8 else -1 }
        ) {
            ThermalExpansionCalculator()
        }
    }
}