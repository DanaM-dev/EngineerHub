package com.danamansour.engineerhub

import androidx.compose.ui.graphics.RectangleShape
import java.time.YearMonth
import androidx.compose.ui.text.TextStyle
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun AcademicCalendarCard(onDateSelected: (String) -> Unit) {
    val currentYearMonth = YearMonth.now()
    val currentYear = currentYearMonth.year
    val currentMonthName =
        currentYearMonth.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
    val totalDays = currentYearMonth.lengthOfMonth()
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val emptySpacesBeforeStart = firstDayOfMonth.dayOfWeek.value - 1

    // Nested Card
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = LightBlueNested),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$currentMonthName $currentYear",
                fontFamily = FontFamily.Cursive,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = LightBlueMain,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.7f),
                        offset = Offset(2f, 2f),
                        blurRadius = 1f
                    )
            )
            )
            Spacer(modifier = Modifier.height(12.dp))

            val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in daysOfWeek) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f).padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.SansSerif,
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val gridItems = List(emptySpacesBeforeStart) { null } + (1..totalDays).toList()
            val columns = 7
            val rows = gridItems.chunked(columns)

            for (row in rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (day in row) {
                        if (day == null) {
                            Spacer(modifier = Modifier.weight(1f).padding(2.dp))
                        } else {
                            Button(
                                onClick = { onDateSelected("$currentMonthName $day, $currentYear") },
                                modifier = Modifier.weight(1f).padding(2.dp),
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = LightBlueMain ),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "$day",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    repeat(columns - row.size) {
                        Spacer(modifier = Modifier.weight(1f).padding(2.dp))
                    }
                }
            }
        }
    }
}