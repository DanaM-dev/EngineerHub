package com.danamansour.engineerhub

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip


data class ConstantItem(
    val name: String,
    val symbol: String,
    val value: String,
    val unit: String,
    val category: String,
    val description: String
)

val engineeringConstants = listOf(
    // Universal & Physical
    ConstantItem("Speed of Light in Vacuum", "c", "2.99792458 × 10⁸", "m/s", "Physics", "Exact speed of electromagnetic radiation in vacuum"),
    ConstantItem("Gravitational Acceleration", "g", "9.80665", "m/s²", "Physics", "Standard acceleration due to gravity on Earth surface"),
    ConstantItem("Newtonian Gravitational Constant", "G", "6.67430 × 10⁻¹¹", "m³/(kg·s²)", "Physics", "Gravitational constant"),

    // Electromagnetic & Quantum
    ConstantItem("Vacuum Permittivity", "ε₀", "8.854187817 × 10⁻¹²", "F/m", "Physics", "Electric constant in free space"),
    ConstantItem("Vacuum Permeability", "μ₀", "1.256637062 × 10⁻⁶", "H/m (N/A²)", "Physics", "Magnetic constant in free space"),
    ConstantItem("Elementary Charge", "e", "1.602176634 × 10⁻¹⁹", "C", "Fundamental", "Electric charge carried by a single proton"),
    ConstantItem("Planck Constant", "h", "6.62607015 × 10⁻³⁴", "J·s", "Fundamental", "Quantum of electromagnetic action"),
    ConstantItem("Electron Mass", "m_e", "9.1093837015 × 10⁻³¹", "kg", "Fundamental", "Rest mass of an electron"),

    // Thermodynamics & Chemical
    ConstantItem("Universal Gas Constant", "R", "8.314462618", "J/(mol·K)", "Thermodynamics", "Constant in ideal gas equation PV = nRT"),
    ConstantItem("Avogadro Constant", "N_A", "6.02214076 × 10²³", "mol⁻¹", "Fundamental", "Number of particles per mole"),
    ConstantItem("Boltzmann Constant", "k_B", "1.380649 × 10⁻²³", "J/K", "Thermodynamics", "Relates particle kinetic energy with temperature"),
    ConstantItem("Stefan-Boltzmann Constant", "σ", "5.670374419 × 10⁻⁸", "W/(m²·K⁴)", "Thermodynamics", "Blackbody radiation energy density"),

    // Material Properties & Reference Standards
    ConstantItem("Standard Atmosphere", "1 atm", "101,325", "Pa", "Materials", "Standard atmospheric pressure at sea level"),
    ConstantItem("Density of Pure Water (4°C)", "ρ_water", "999.97", "kg/m³", "Materials", "Peak density of water"),
    ConstantItem("Specific Heat of Water", "c_p", "4184", "J/(kg·K)", "Materials", "Heat capacity of liquid water at 20°C"),
    ConstantItem("Speed of Sound in Air (20°C)", "v_sound", "343.2", "m/s", "Materials", "Acoustic wave velocity in dry air"),

    // Mathematical & Numbers
    ConstantItem("Pi", "π", "3.1415926535", "-", "Math", "Circle circumference to diameter ratio"),
    ConstantItem("Euler's Number", "e", "2.7182818284", "-", "Math", "Base of the natural logarithm")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsSheetScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val clipboardManager = LocalClipboardManager.current

    val categories = listOf("All", "Physics", "Fundamental", "Thermodynamics", "Materials", "Math")

    val filteredConstants = engineeringConstants.filter { item ->
        val matchesCategory = (selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true))
        val matchesQuery = item.name.contains(searchQuery, ignoreCase = true) ||
                item.symbol.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Constants & Reference",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by name, symbol, or property...") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category, fontWeight = FontWeight.SemiBold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        labelColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedCategory == category,
                        borderColor = MaterialTheme.colorScheme.primary,
                        selectedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredConstants) { item ->
                ConstantCard(
                    item = item,
                    onCopy = {
                        clipboardManager.setText(AnnotatedString("${item.symbol} = ${item.value} ${item.unit}"))
                    }
                )
            }
        }
    }
}

@Composable
fun ConstantCard(item: ConstantItem, onCopy: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onCopy,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy Constant",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${item.symbol} =",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${item.value} ${item.unit}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}