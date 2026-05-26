package com.example.serenespace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_table")
data class MoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val emoji: String,          // e.g., "😊"
    val moodType: String,       // e.g., "Happy", "Anxious"
    val note: String = "",      // Optional text note
    val timestamp: Long         // System.currentTimeMillis()
)