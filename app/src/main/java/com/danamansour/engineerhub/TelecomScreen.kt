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
fun TelecomScreen() {
    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Telecommunications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        ExpandableCalculatorCard(
            title = "IPv4 Subnet Calculator",
            description = "Calculate Network, Broadcast & Host Range",
            icon = Icons.Outlined.Lan,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            SubnetCalculator()
        }


        ExpandableCalculatorCard(
            title = "Shannon-Hartley Capacity",
            description = "Calculate Channel Capacity (C = B · log₂(1 + SNR))",
            icon = Icons.Outlined.NetworkCheck,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            ShannonCapacityCalculator()
        }


        ExpandableCalculatorCard(
            title = "Free Space Path Loss (FSPL)",
            description = "Calculate radio frequency signal loss (dB)",
            icon = Icons.Outlined.CellTower,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            FSPLCalculator()
        }


        ExpandableCalculatorCard(
            title = "Data Transfer Time",
            description = "Calculate transfer duration based on file size and speed",
            icon = Icons.Outlined.Download,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            DataTransferCalculator()
        }


        ExpandableCalculatorCard(
            title = "dBm ↔ Watts Converter",
            description = "Convert RF power between decibel-milliwatts and watts",
            icon = Icons.Outlined.PowerSettingsNew,
            expanded = expandedIndex == 4,
            onExpandChanged = { expandedIndex = if (it) 4 else -1 }
        ) {
            DbmWattsCalculator()
        }


        ExpandableCalculatorCard(
            title = "Logic Gate Truth Evaluator",
            description = "Evaluate binary logic gates (AND, OR, XOR, etc.)",
            icon = Icons.Outlined.Memory,
            expanded = expandedIndex == 5,
            onExpandChanged = { expandedIndex = if (it) 5 else -1 }
        ) {
            LogicGateCalculator()
        }
    }
}