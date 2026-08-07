package com.danamansour.engineerhub

import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height

@Composable
fun AcademicCalendarCard(onDateSelected: (String) -> Unit) {

    val currentYearMonth = YearMonth.now()
    val currentYear = currentYearMonth.year
    val currentMonthName = currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val totalDays = currentYearMonth.lengthOfMonth()

    // 1. Find out what day of the week the 1st of the month is
    val firstDayOfMonth = currentYearMonth.atDay(1)
    // 2. Calculate empty spaces
    val emptySpacesBeforeStart = firstDayOfMonth.dayOfWeek.value - 1

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "$currentMonthName $currentYear", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            // Day headers
            val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in daysOfWeek) {
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val gridItems = List(emptySpacesBeforeStart) { null } + (1..totalDays).toList()

            val columns = 7
            val rows = gridItems.chunked(columns)


            for (row in rows) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (day in row) {
                        if (day == null) {
                            // Print an invisible spacer for empty days before the 1st
                            Spacer(modifier = Modifier.weight(1f).padding(2.dp))
                        } else {
                            Button(
                                onClick = { onDateSelected("$currentMonthName $day, $currentYear") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp) //anti squishing
                            ) {
                                Text(text = "$day", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        // Fill remaining empty space in the last row
                        repeat(columns - row.size) {
                            Spacer(modifier = Modifier.weight(1f).padding(2.dp))
                        }
                    }
                }
            }
        }

    }
}