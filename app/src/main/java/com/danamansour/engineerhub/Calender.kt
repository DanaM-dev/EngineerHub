package com.danamansour.engineerhub

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@Composable
fun AcademicCalendarCard(
    events: List<EngineerEvent> = emptyList(),
    onDateSelected: (String) -> Unit
) {
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
        ) {
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
                        val formattedDate = "$currentMonthName $day, $currentYear"
                        val dayEvents = events.filter { it.date == formattedDate }

                        val isToday = day == today.dayOfMonth &&
                                displayedYearMonth.year == today.year &&
                                displayedYearMonth.month == today.month

                        Button(
                            onClick = { onDateSelected(formattedDate) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .padding(2.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isToday) GradientEnd else Color.Transparent,
                                contentColor = if (isToday) Color.White else GradientEnd
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "$day",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )


                                if (dayEvents.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        dayEvents.take(3).forEach { event ->
                                            val dotColor = if (isToday) {
                                                Color.White
                                            } else {
                                                when (event.category) {
                                                    "Exam" -> Color(0xFFD32F2F)
                                                    "Quiz" -> Color(0xFFF57C00)
                                                    "Project" -> Color(0xFF0288D1)
                                                    else -> Color(0xFF388E3C)
                                                }
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .size(4.dp)
                                                    .background(dotColor, CircleShape)
                                            )
                                        }
                                    }
                                }
                            }
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