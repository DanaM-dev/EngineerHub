package com.danamansour.engineerhub

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDate


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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBlueAccent
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$currentMonthName $currentYear",
                fontFamily = FontFamily.SansSerif,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = PrimaryBlue,
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
                            color = PrimaryBlue
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
                            val isToday = day == LocalDate.now().dayOfMonth
                            Button(
                                onClick = { onDateSelected("$currentMonthName $day, $currentYear") },
                                modifier = Modifier.weight(1f).padding(2.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if(isToday) PrimaryBlue
                                        else Color.White,
                                    contentColor =
                                        if(isToday) Color.White
                                        else PrimaryBlue,),
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