package com.danamansour.engineerhub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import java.time.LocalDate
import java.util.Locale

val GradientStart = Color(0xFF42E8E0) // Cyan
val GradientEnd = Color(0xFF5C9CE6) // Soft Blue
val CalendarHighlight = Color(0xFF29B6F6) // The bright blue for the selected circle

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
                        colors = listOf(GradientStart, GradientEnd)
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
                    .padding(top = 24.dp), // Slight push down to clear the menu icon
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
                    val currentMonthName = LocalDate.now().month.getDisplayName(java.time.format.TextStyle.FULL, currentLocale)
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
                fontWeight = FontWeight.Light,
                fontSize = 22.sp,
                color = TextPrimaryDark,
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (eventList.isEmpty()) {
                Text(
                    text = "No upcoming events scheduled.",
                    color = TextSoftGray,
                    fontSize = 14.sp
                )
            } else {
                for (event in eventList) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val categoryColor = when(event.category){
                            "Exam" -> Color.Red
                            "Project" -> CalendarHighlight
                            "Quiz" -> Color(0xFFFFB300)
                            else -> Color.Green
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = event.date, fontSize = 12.sp, color = TextSoftGray)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "[${event.category}] ${event.description}",
                                fontSize = 16.sp,
                                color = categoryColor,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        TextButton(onClick = { eventList.remove(event) }) {
                            Text("Delete", color = Color(0xFFE57373))
                        }
                    }
                }
            }
        }
    }


    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(text = "Add Event") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = selectedDate, color = TextSoftGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = customDescription,
                        onValueChange = { customDescription = it },
                        label = { Text("Event Details") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Exam", customDescription.ifEmpty { "Final Exam" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Add Exam") }
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Project", customDescription.ifEmpty { "Milestone" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Add Project") }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Quiz", customDescription.ifEmpty { "Pop Quiz" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Add Quiz") }
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Study", customDescription.ifEmpty { "Revision" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Add Study") }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }
}