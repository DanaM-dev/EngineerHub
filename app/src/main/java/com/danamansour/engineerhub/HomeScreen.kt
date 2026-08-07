package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DashboardBackground = Color(0xFFF7F9FC)
val CardBackground = Color(0xFFFFFFFF)

val PrimaryBlue = Color(0xFF1565C0)
val LightBlueAccent = Color(0xFFE3F2FD)

val TextPrimary = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF757575)

data class EngineerEvent(
    val date: String,
    val category: String,
    val description: String
)

@Composable
fun HomeScreen() {
    val eventList = remember { mutableStateListOf<EngineerEvent>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Today") }
    var customDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dashboard text removed as requested!
        Spacer(modifier = Modifier.height(8.dp))

        // MAIN CALENDAR CONTAINER (Square edges, darker light-blue)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ){
            Column(modifier = Modifier.padding(16.dp)) {

                // NESTED CALENDAR GRID (Lighter blue, square edges)
                AcademicCalendarCard(onDateSelected = { date ->
                    selectedDate = date
                    showAddDialog = true
                })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // UPCOMING EVENTS (Kept round as requested, using LightBlueMain)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Upcoming Events",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = TextPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (eventList.isEmpty()) {
                    Text(text = "• No upcoming events scheduled.", fontFamily = FontFamily.SansSerif)
                } else {
                    for (event in eventList) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val categoryColor = when(event.category){
                                "Exam" -> Color.Red
                                "Project" -> Color.Blue
                                "Quiz" -> Color.Yellow
                                else -> Color.Green
                            }
                            Text(
                                text = "• [${event.category}] ${event.date}: ${event.description}",
                                modifier = Modifier.weight(1f),
                                fontFamily = FontFamily.SansSerif,
                                color = categoryColor
                            )
                            TextButton(onClick = { eventList.remove(event) }) {
                                Text("Delete", color = MaterialTheme.colorScheme.error)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }

    // THE POPUP DIALOG
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(text = "Add Event", fontFamily = FontFamily.SansSerif)
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = selectedDate, fontFamily = FontFamily.SansSerif, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    // THE NEW WHITE TEXT BOX
                    OutlinedTextField(
                        value = customDescription,
                        onValueChange = { customDescription = it },
                        label = { Text("Event Details") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2x2 BUTTON GRID
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Top Row
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Exam", customDescription.ifEmpty { "Final Exam" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Add as Exam", textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Project", customDescription.ifEmpty { "Milestone" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Add as Project", textAlign = TextAlign.Center)
                            }
                        }
                        // Bottom Row
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Quiz", customDescription.ifEmpty { "Pop Quiz" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Add as Quiz", textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = {
                                    eventList.add(EngineerEvent(selectedDate, "Study", customDescription.ifEmpty { "Revision" }))
                                    customDescription = ""
                                    showAddDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Add as Study", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}


