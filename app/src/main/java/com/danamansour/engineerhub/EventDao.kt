package com.danamansour.engineerhub


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events_table")
    fun getAllEvents(): Flow<List<EngineerEvent>>

    @Insert
    suspend fun insertEvent(event: EngineerEvent)

    @Delete
    suspend fun deleteEvent(event: EngineerEvent)
}