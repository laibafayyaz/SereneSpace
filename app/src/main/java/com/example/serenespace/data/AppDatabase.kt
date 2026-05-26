package com.example.serenespace.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MoodEntity::class, JournalEntity::class, SafetyPlanEntity::class], // Added SafetyPlanEntity
    version = 3, // Incremented from 2 to 3
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun journalDao(): JournalDao
    abstract fun safetyPlanDao(): SafetyPlanDao // Added SafetyPlanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "serene_space_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}