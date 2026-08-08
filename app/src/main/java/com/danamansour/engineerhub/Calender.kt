package com.danamansour.engineerhub

import java.time.YearMonth
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDate


@Composable
fun AcademicCalendarCard(onDateSelected: (String) -> Unit) {
    val currentYearMonth = YearMonth.now()
    val currentYear = currentYearMonth.year
    val currentMonthName = currentYearMonth.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
    val totalDays = currentYearMonth.lengthOfMonth()
    val firstDayOfMonth = currentYearMonth.atDay(1)
    val emptySpacesBeforeStart = firstDayOfMonth.dayOfWeek.value - 1


    Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {


        Text(
            text = "$currentMonthName $currentYear",
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Days of the week in crisp Blue
        val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (day in daysOfWeek) {
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    color = CalendarHighlight
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                        Spacer(modifier = Modifier.weight(1f).padding(4.dp))
                    } else {
                        val isToday = day == LocalDate.now().dayOfMonth
                        Button(
                            onClick = { onDateSelected("$currentMonthName $day, $currentYear") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isToday) CalendarHighlight else Color.Transparent,
                                contentColor = if (isToday) Color.White else CalendarHighlight, // Blue numbers for regular days
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "$day",
                                fontWeight = FontWeight.Bold ,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                repeat(columns - row.size) {
                    Spacer(modifier = Modifier.weight(1f).padding(4.dp))
                }
            }
        }
    }
}