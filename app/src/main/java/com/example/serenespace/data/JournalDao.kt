package com.example.serenespace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Insert
    suspend fun insertJournal(journal: JournalEntity)

    @Query("SELECT * FROM journal_table ORDER BY timestamp DESC")
    fun getAllJournals(): Flow<List<JournalEntity>>
}