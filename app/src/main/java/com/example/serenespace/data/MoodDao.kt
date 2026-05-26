package com.example.serenespace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert
    suspend fun insertMood(mood: MoodEntity)

    @Query("SELECT * FROM mood_table ORDER BY timestamp DESC")
    fun getAllMoods(): Flow<List<MoodEntity>>
}