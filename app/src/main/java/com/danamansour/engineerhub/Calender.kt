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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


@Composable
fun AcademicCalendarCard(onDateSelected: (String) -> Unit) {
    var displayedYearMonth by remember { mutableStateOf(YearMonth.now()) }

    val currentYear = displayedYearMonth.year
    val currentMonthName = displayedYearMonth.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
    val totalDays = displayedYearMonth.lengthOfMonth()
    val firstDayOfMonth = displayedYearMonth.atDay(1)
    val emptySpacesBeforeStart = firstDayOfMonth.dayOfWeek.value % 7


    Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Previous Month Button (<)
            IconButton(
                onClick = { displayedYearMonth = displayedYearMonth.minusMonths(1) },
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    tint = CalendarHighlight
                )
            }

        Text(
            text = "$currentMonthName $currentYear",
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark,
            textAlign = TextAlign.Center
        )
            IconButton(
                onClick = { displayedYearMonth = displayedYearMonth.plusMonths(1) },
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = CalendarHighlight
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


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
                    color = GradientEnd
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val gridItems = List(emptySpacesBeforeStart) { null } + (1..totalDays).toList()
        val columns = 7
        val rows = gridItems.chunked(columns)
        val today = LocalDate.now()

        for (row in rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in row) {
                    if (day == null) {
                        Spacer(modifier = Modifier.weight(1f).padding(4.dp))
                    } else {
                        //highlight today's date iff viewing current month & year
                        val isToday = day == today.dayOfMonth &&
                                displayedYearMonth.year == today.year &&
                                displayedYearMonth.month == today.month
                        Button(
                            onClick = { onDateSelected("$currentMonthName $day, $currentYear") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isToday) GradientEnd else Color.Transparent,
                                contentColor = if (isToday) Color.White else GradientEnd, // Blue numbers for regular days
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