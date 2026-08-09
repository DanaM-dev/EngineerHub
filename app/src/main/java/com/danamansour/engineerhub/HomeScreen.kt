package com.danamansour.engineerhub

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

val GradientEnd = Color(0xFF5C9CE6) // Soft Blue
val CalendarHighlight = Color(0xFF29B6F6) // bright blue

val TextPrimaryDark = Color(0xFF4A4A4A) // Soft dark gray for main text
val TextSoftGray = Color(0xFF9E9E9E) // Light gray for inactive text

data class EngineerEvent(
    val date: String,
    val category: String,
    val description: String
)

@Composable
fun HomeScreen(onMenuClick: () -> Unit = {}) {
    val eventList = remember { mutableStateListOf<EngineerEvent>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Today") }
    var customDescription by remember { mutableStateOf("") }
    val config = LocalConfiguration.current
    val currentLocale = config.locales[0]

    var selectedCategory by remember { mutableStateOf("Exam") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .offset(y = (-48).dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(GradientEnd, Color(0xFF0061A4), GradientEnd, Color(0xFF0061A4))
                    ),
                    shape = WavyHeaderShape()
                )
                .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Menu",
                    tint = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format(currentLocale, "%02d", LocalDate.now().dayOfMonth),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )

                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )

                Column {
                    val currentMonthName = LocalDate.now().month.getDisplayName(
                        java.time.format.TextStyle.FULL,
                        currentLocale
                    )
                    Text(
                        text = currentMonthName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = "${LocalDate.now().year}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            AcademicCalendarCard(onDateSelected = { date ->
                selectedDate = date
                showAddDialog = true
            })

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Upcoming Events",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = TextPrimaryDark,
            )

            Spacer(modifier = Modifier.height(16.dp))


            if (eventList.isEmpty()) {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFF9FAFB)),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No upcoming events scheduled.",
                            color = TextSoftGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (event in eventList) {
                        EventCard(
                            event = event,
                            onDelete = { eventList.remove(event) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                customDescription = ""
                selectedCategory = "Exam"
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            title = {
                Text(
                    text = "Add Event",
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = TextPrimaryDark
                )
            },
            text = {
                Column {
                    Text(
                        text = selectedDate,
                        color = CalendarHighlight,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = "Event type", color = TextSoftGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedCategory == "Exam",
                                    onClick = { selectedCategory = "Exam" },
                                    colors = RadioButtonDefaults.colors(selectedColor = CalendarHighlight)
                                )
                                Text("Exam", color = TextPrimaryDark, fontSize = 15.sp)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedCategory == "Quiz",
                                    onClick = { selectedCategory = "Quiz" },
                                    colors = RadioButtonDefaults.colors(selectedColor = CalendarHighlight)
                                )
                                Text("Quiz", color = TextPrimaryDark, fontSize = 15.sp)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedCategory == "Project",
                                    onClick = { selectedCategory = "Project" },
                                    colors = RadioButtonDefaults.colors(selectedColor = CalendarHighlight)
                                )
                                Text("Project", color = TextPrimaryDark, fontSize = 15.sp)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedCategory == "Study",
                                    onClick = { selectedCategory = "Study" },
                                    colors = RadioButtonDefaults.colors(selectedColor = CalendarHighlight)
                                )
                                Text("Study", color = TextPrimaryDark, fontSize = 15.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = customDescription,
                        onValueChange = { customDescription = it },
                        placeholder = { Text("Event description...", color = TextSoftGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CalendarHighlight,
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val defaultDesc = when (selectedCategory) {
                            "Exam" -> "Final Exam"
                            "Project" -> "Milestone"
                            "Quiz" -> "Pop Quiz"
                            else -> "Revision"
                        }

                        eventList.add(
                            EngineerEvent(
                                selectedDate,
                                selectedCategory,
                                customDescription.ifEmpty { defaultDesc })
                        )

                        customDescription = ""
                        selectedCategory = "Exam"
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CalendarHighlight),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Add", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                        customDescription = ""
                        selectedCategory = "Exam"
                    }
                ) {
                    Text("Cancel", color = TextSoftGray)
                }
            }
        )
    }
}

@Composable
fun EventCard(
    event: EngineerEvent,
    onDelete: () -> Unit
) {

    val (primaryColor, backgroundColor) = when (event.category) {
        "Exam" -> Color(0xFFD32F2F) to Color(0xFFFFEBEE)
        "Project" -> Color(0xFF0288D1) to Color(0xFFE1F5FE)
        "Quiz" -> Color(0xFFF57C00) to Color(0xFFFFF3E0)
        else -> Color(0xFF388E3C) to Color(0xFFE8F5E9)
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFEEF0F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(38.dp)
                    .background(primaryColor, shape = RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Surface(
                        color = backgroundColor,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = event.category.uppercase(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }


                    Text(
                        text = event.date,
                        fontSize = 12.sp,
                        color = TextSoftGray,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = event.description,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )
            }


            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Event",
                    tint = Color(0xFFB0BEC5),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}