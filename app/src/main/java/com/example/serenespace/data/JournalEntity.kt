package com.example.serenespace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_table")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val encryptedContent: String, // The encrypted text
    val moodTag: String,          // e.g., "Grateful", "Anxious"
    val timestamp: Long
)