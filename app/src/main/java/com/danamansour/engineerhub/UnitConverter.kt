package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class UnitCategory(val displayName: String, val units: List<String>) {
    LENGTH("Length", listOf("Meters (m)", "Kilometers (km)", "Centimeters (cm)", "Feet (ft)", "Inches (in)", "Miles (mi)")),
    MASS("Mass", listOf("Kilograms (kg)", "Grams (g)", "Pounds (lbs)", "Ounces (oz)", "Metric Tons (t)")),
    PRESSURE("Pressure", listOf("Pascals (Pa)", "Kilopascals (kPa)", "Bar", "PSI (lbf/in²)", "Atmospheres (atm)")),
    POWER("Power", listOf("Watts (W)", "Kilowatts (kW)", "Horsepower (hp)", "BTU/hr")),
    ENERGY("Energy", listOf("Joules (J)", "Kilojoules (kJ)", "Kilowatt-hours (kWh)", "BTU", "Calories (cal)")),
    TEMPERATURE("Temperature", listOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")),
    DATA_RATES("Data Rates", listOf("Bits/sec (bps)", "Kilobits/sec (Kbps)", "Megabits/sec (Mbps)", "Gigabits/sec (Gbps)", "Megabytes/sec (MB/s)"))
}

@Composable
fun UnitConverterScreen() {
    var selectedCategory by remember { mutableStateOf(UnitCategory.LENGTH) }
    var fromUnit by remember { mutableStateOf(selectedCategory.units[0]) }
    var toUnit by remember { mutableStateOf(selectedCategory.units[1]) }
    var inputValue by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategory) {
        fromUnit = selectedCategory.units[0]
        toUnit = selectedCategory.units.getOrElse(1) { selectedCategory.units[0] }
        inputValue = ""
        resultText = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Engineering Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        CategoryDropdown(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        EngineerCalculatorTemplate(
            title = "${selectedCategory.displayName} Conversion",
            subtitle = "From: $fromUnit ➔ To: $toUnit",
            onCalculate = {
                val input = inputValue.toDoubleOrNull()
                if (input != null) {
                    val converted = performConversion(selectedCategory, fromUnit, toUnit, input)
                    resultText = "${String.format("%.6g", input)} $fromUnit =\n${String.format("%.6g", converted)} $toUnit"
                    isError = false
                } else {
                    resultText = "Invalid Value"
                    isError = true
                }
            },
            resultText = resultText,
            isError = isError
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                EngineerTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    label = "Value to Convert",
                    modifier = Modifier.fillMaxWidth()
                )

                UnitDropdownMenu(
                    label = "From Unit",
                    selectedUnit = fromUnit,
                    units = selectedCategory.units,
                    onUnitSelected = { fromUnit = it },
                    modifier = Modifier.fillMaxWidth()
                )

                UnitDropdownMenu(
                    label = "To Unit",
                    selectedUnit = toUnit,
                    units = selectedCategory.units,
                    onUnitSelected = { toUnit = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


        ConversionReferenceCard(category = selectedCategory)
    }
}

@Composable
fun ConversionReferenceCard(category: UnitCategory) {
    val references = getReferenceData(category)

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "📌 ${category.displayName} Quick References",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            references.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun getReferenceData(category: UnitCategory): List<Pair<String, String>> {
    return when (category) {
        UnitCategory.LENGTH -> listOf(
            "1 Meter (m)" to "3.28084 ft",
            "1 Kilometer (km)" to "0.621371 mi",
            "1 Inch (in)" to "2.54 cm",
            "1 Mile (mi)" to "1.60934 km"
        )
        UnitCategory.MASS -> listOf(
            "1 Kilogram (kg)" to "2.20462 lbs",
            "1 Metric Ton (t)" to "1,000 kg",
            "1 Pound (lb)" to "16 oz",
            "1 Ounce (oz)" to "28.3495 g"
        )
        UnitCategory.PRESSURE -> listOf(
            "Standard Atm (1 atm)" to "101.325 kPa",
            "1 Atmosphere" to "14.696 PSI",
            "1 Bar" to "100,000 Pa",
            "1 PSI" to "6.89476 kPa"
        )
        UnitCategory.POWER -> listOf(
            "1 Horsepower (hp)" to "745.7 Watts",
            "1 Kilowatt (kW)" to "1.34102 hp",
            "1 BTU/hr" to "0.293071 W",
            "1 kW" to "3,412.14 BTU/hr"
        )
        UnitCategory.ENERGY -> listOf(
            "1 kWh" to "3.6 × 10⁶ Joules",
            "1 BTU" to "1,055.06 Joules",
            "1 Calorie (cal)" to "4.184 Joules",
            "1 Kilojoule (kJ)" to "0.239 kcal"
        )
        UnitCategory.TEMPERATURE -> listOf(
            "Water Freezing" to "0°C / 32°F / 273.15K",
            "Water Boiling" to "100°C / 212°F / 373.15K",
            "Celsius to Fahrenheit" to "(°C × 9/5) + 32",
            "Celsius to Kelvin" to "°C + 273.15"
        )
        UnitCategory.DATA_RATES -> listOf(
            "1 Byte (B)" to "8 Bits (b)",
            "1 Megabyte/s (MB/s)" to "8 Megabits/s (Mbps)",
            "1 Gbps" to "1,000 Mbps",
            "1 Mbps" to "1,000 Kbps"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    selectedCategory: UnitCategory,
    onCategorySelected: (UnitCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCategory.displayName,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("Select Conversion Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            UnitCategory.values().forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.displayName) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdownMenu(
    label: String,
    selectedUnit: String,
    units: List<String>,
    onUnitSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedUnit,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

private fun performConversion(
    category: UnitCategory,
    from: String,
    to: String,
    value: Double
): Double {
    if (from == to) return value

    return when (category) {
        UnitCategory.LENGTH -> {
            val meters = when (from) {
                "Kilometers (km)" -> value * 1000.0
                "Centimeters (cm)" -> value / 100.0
                "Feet (ft)" -> value * 0.3048
                "Inches (in)" -> value * 0.0254
                "Miles (mi)" -> value * 1609.344
                else -> value
            }
            when (to) {
                "Kilometers (km)" -> meters / 1000.0
                "Centimeters (cm)" -> meters * 100.0
                "Feet (ft)" -> meters / 0.3048
                "Inches (in)" -> meters / 0.0254
                "Miles (mi)" -> meters / 1609.344
                else -> meters
            }
        }

        UnitCategory.MASS -> {
            val kg = when (from) {
                "Grams (g)" -> value / 1000.0
                "Pounds (lbs)" -> value * 0.453592
                "Ounces (oz)" -> value * 0.0283495
                "Metric Tons (t)" -> value * 1000.0
                else -> value
            }
            when (to) {
                "Grams (g)" -> kg * 1000.0
                "Pounds (lbs)" -> kg / 0.453592
                "Ounces (oz)" -> kg / 0.0283495
                "Metric Tons (t)" -> kg / 1000.0
                else -> kg
            }
        }

        UnitCategory.PRESSURE -> {
            val pa = when (from) {
                "Kilopascals (kPa)" -> value * 1000.0
                "Bar" -> value * 100000.0
                "PSI (lbf/in²)" -> value * 6894.76
                "Atmospheres (atm)" -> value * 101325.0
                else -> value
            }
            when (to) {
                "Kilopascals (kPa)" -> pa / 1000.0
                "Bar" -> pa / 100000.0
                "PSI (lbf/in²)" -> pa / 6894.76
                "Atmospheres (atm)" -> pa / 101325.0
                else -> pa
            }
        }

        UnitCategory.POWER -> {
            val watts = when (from) {
                "Kilowatts (kW)" -> value * 1000.0
                "Horsepower (hp)" -> value * 745.7
                "BTU/hr" -> value * 0.293071
                else -> value
            }
            when (to) {
                "Kilowatts (kW)" -> watts / 1000.0
                "Horsepower (hp)" -> watts / 745.7
                "BTU/hr" -> watts / 0.293071
                else -> watts
            }
        }

        UnitCategory.ENERGY -> {
            val joules = when (from) {
                "Kilojoules (kJ)" -> value * 1000.0
                "Kilowatt-hours (kWh)" -> value * 3600000.0
                "BTU" -> value * 1055.06
                "Calories (cal)" -> value * 4.184
                else -> value
            }
            when (to) {
                "Kilojoules (kJ)" -> joules / 1000.0
                "Kilowatt-hours (kWh)" -> joules / 3600000.0
                "BTU" -> joules / 1055.06
                "Calories (cal)" -> joules / 4.184
                else -> joules
            }
        }

        UnitCategory.TEMPERATURE -> {
            val celsius = when (from) {
                "Fahrenheit (°F)" -> (value - 32.0) * 5.0 / 9.0
                "Kelvin (K)" -> value - 273.15
                else -> value
            }
            when (to) {
                "Fahrenheit (°F)" -> (celsius * 9.0 / 5.0) + 32.0
                "Kelvin (K)" -> celsius + 273.15
                else -> celsius
            }
        }

        UnitCategory.DATA_RATES -> {
            val bps = when (from) {
                "Kilobits/sec (Kbps)" -> value * 1e3
                "Megabits/sec (Mbps)" -> value * 1e6
                "Gigabits/sec (Gbps)" -> value * 1e9
                "Megabytes/sec (MB/s)" -> value * 8e6
                else -> value
            }
            when (to) {
                "Kilobits/sec (Kbps)" -> bps / 1e3
                "Megabits/sec (Mbps)" -> bps / 1e6
                "Gigabits/sec (Gbps)" -> bps / 1e9
                "Megabytes/sec (MB/s)" -> bps / 8e6
                else -> bps
            }
        }
    }
}