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
fun IndustrialScreen() {

    var expandedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Industrial Engineering",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))


        ExpandableCalculatorCard(
            title = "Economic Order Quantity (EOQ)",
            description = "Calculate optimal inventory order quantity",
            icon = Icons.Outlined.Inventory2,
            expanded = expandedIndex == 0,
            onExpandChanged = { expandedIndex = if (it) 0 else -1 }
        ) {
            EoqCalculator()
        }


        ExpandableCalculatorCard(
            title = "Overall Equipment Effectiveness",
            description = "Calculate OEE from availability, performance, and quality",
            icon = Icons.Outlined.Assessment,
            expanded = expandedIndex == 1,
            onExpandChanged = { expandedIndex = if (it) 1 else -1 }
        ) {
            OeeCalculator()
        }

        ExpandableCalculatorCard(
            title = "Takt Time Calculator",
            description = "Calculate production rate required to meet customer demand",
            icon = Icons.Outlined.Timer,
            expanded = expandedIndex == 2,
            onExpandChanged = { expandedIndex = if (it) 2 else -1 }
        ) {
            TaktTimeCalculator()
        }


        ExpandableCalculatorCard(
            title = "Break-Even Analysis",
            description = "Calculate break-even units and target revenue",
            icon = Icons.Outlined.TrendingUp,
            expanded = expandedIndex == 3,
            onExpandChanged = { expandedIndex = if (it) 3 else -1 }
        ) {
            BreakEvenCalculator()
        }
    }
}