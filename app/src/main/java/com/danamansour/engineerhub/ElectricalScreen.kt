package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ElectricalScreen() {
    // tracks which calculator is currently open (-1 = all collapsed)
    // i can default it to 0 if i want the 1st one to be open , but i don't
    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Electrical Engineering",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        ExpandableCalculatorCard(
            title = "Ohm's Law",
            description = "Calculate Voltage (V = I × R)",
            icon = Icons.Outlined.Bolt,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            VoltageCalculator()
        }


        ExpandableCalculatorCard(
            title = "Series R & C",
            description = "Calculates Series Resistance OR Capacitance",
            icon = Icons.Outlined.Hub,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            SeriesComponentCalculator()
        }


        ExpandableCalculatorCard(
            title = "Parallel R & C",
            description = "Calculates Parallel Resistance OR Capacitance",
            icon = Icons.Outlined.GridOn,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            ParallelComponentCalculator()
        }


        ExpandableCalculatorCard(
            title = "Voltage Divider",
            description = "V_out = V_in * (R2 / (R1 + R2))",
            icon = Icons.Outlined.CallSplit,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            VoltageDividerCalculator()
        }


        ExpandableCalculatorCard(
            title = "Time Constant",
            description = "RC (Capacitor) or RL (Inductor)",
            icon = Icons.Outlined.Timer,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            TimeConstantCalculator()
        }


        ExpandableCalculatorCard(
            title = "RLC Resonant Frequency",
            description = "f0 = 1 / (2π√(LC))",
            icon = Icons.Outlined.Waves,
            expanded = expandedIndex == 5,
            onExpandChanged = { expandedIndex = if (it) 5 else -1 }
        ) {
            ResonantFrequencyCalculator()
        }


        ExpandableCalculatorCard(
            title = "Resistor Decoder",
            description = "4-Band color code calculator",
            icon = Icons.Outlined.Palette,
            expanded = expandedIndex == 6,
            onExpandChanged = { expandedIndex = if (it) 6 else -1 }
        ) {
            ResistorColorCodeCalculator()
        }


        ExpandableCalculatorCard(
            title = "AC Power Factor",
            description = "Calculates P, Q, and S components",
            icon = Icons.Outlined.Power,
            expanded = expandedIndex == 7,
            onExpandChanged = { expandedIndex = if (it) 7 else -1 }
        ) {
            ACPowerCalculator()
        }


        ExpandableCalculatorCard(
            title = "Op-Amp Gain",
            description = "Inverting and Non-Inverting configurations",
            icon = Icons.Outlined.Memory,
            expanded = expandedIndex == 8,
            onExpandChanged = { expandedIndex = if (it) 8 else -1 }
        ) {
            OpAmpGainCalculator()
        }


        ExpandableCalculatorCard(
            title = "PCB Trace Width (IPC-2221)",
            description = "Outer Layer track width sizing",
            icon = Icons.Outlined.Router,
            expanded = expandedIndex == 9,
            onExpandChanged = { expandedIndex = if (it) 9 else -1 }
        ) {
            PCBTraceWidthCalculator()
        }
    }
}