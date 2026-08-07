package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.text.ifEmpty

data class EngineerEvent(
    val date: String,
    val category: String,
    val description: String
)

@Composable
fun HomeScreen() {

    //lemme hold the event for now lol
    var eventList = remember { mutableStateListOf<EngineerEvent>() }

    // did they click/open to add an event?
    var showAddDialog by remember { mutableStateOf(false) }

    // what date did they select?
    var selectedDate by remember { mutableStateOf("Today") }

    var customDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dashboard", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))


        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Academic Calendar", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))

                AcademicCalendarCard(onDateSelected = { date ->
                    selectedDate = date
                    showAddDialog = true })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Upcoming Events & Deadlines", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))

                if (eventList.isEmpty()) {
                    Text(text = "• No upcoming events scheduled.")
                } else {
                    // Loop & display
                    for (event in eventList) {
                        androidx.compose.foundation.layout.Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "• [${event.category}] ${event.date}: ${event.description}",
                                modifier = Modifier.weight(1f)
                            )
                            // Delete button for individual events
                            androidx.compose.material3.TextButton(
                                onClick = { eventList.remove(event) }
                            ) {
                                Text("Delete", color = MaterialTheme.colorScheme.error)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))


                    }
                }
            }
        }
    }

//Pop-up Dialog for Adding Events
    if (showAddDialog) {                             //if clicked/opened in other words
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(text = "Add Event for $selectedDate") },
            text = {
                Column {
                    Text(text = "Select an event type or add a custom note:")
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customDescription,
                        onValueChange = { customDescription = it },
                        label = { Text("Event Details (e.g., Math 101)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Quick category buttons
                    Button(
                        onClick = {
                            eventList.add(EngineerEvent(selectedDate, "Exam", customDescription.ifEmpty { "Final Exam" }))
                            customDescription = ""
                            showAddDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add as Exam")
                    }
                    Button(
                        onClick = {
                            eventList.add(EngineerEvent(selectedDate, "Quiz", customDescription.ifEmpty { "Pop Quiz" }))
                            customDescription = ""
                            showAddDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add as Quiz")
                    }

                    Button(
                        onClick = {
                            eventList.add(EngineerEvent(selectedDate, "Project", customDescription.ifEmpty { "EngineerHub Milestone" }))
                            customDescription = ""
                            showAddDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add as Project Deadline")
                    }
                    Button(
                        onClick = {
                            eventList.add(EngineerEvent(selectedDate, "Study Day", customDescription.ifEmpty { "Focused Revision" }))
                            customDescription = ""
                            showAddDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add as Study Session")
                    }
                }
            },
            dismissButton = {   //clicked accidentally? we've got your back
                androidx.compose.material3.TextButton(
                    onClick = { showAddDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

