package com.danamansour.engineerhub

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).eventDao()

    val allEvents: StateFlow<List<EngineerEvent>> = dao.getAllEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEvent(event: EngineerEvent) {
        viewModelScope.launch {
            dao.insertEvent(event)
        }
    }

    fun removeEvent(event: EngineerEvent) {
        viewModelScope.launch {
            dao.deleteEvent(event)
        }
    }
}