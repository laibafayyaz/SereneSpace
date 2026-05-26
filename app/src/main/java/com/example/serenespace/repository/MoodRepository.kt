package com.example.serenespace.repository

import com.example.serenespace.CryptoHelper // FIXED IMPORT
import com.example.serenespace.data.JournalDao // FIXED IMPORT
import com.example.serenespace.data.JournalEntity // FIXED IMPORT
import com.example.serenespace.data.MoodDao // FIXED IMPORT
import com.example.serenespace.data.MoodEntity // FIXED IMPORT
import com.example.serenespace.data.SafetyPlanDao // FIXED IMPORT
import com.example.serenespace.data.SafetyPlanEntity // FIXED IMPORT
import kotlinx.coroutines.flow.Flow

class MoodRepository(
    private val moodDao: MoodDao,
    private val journalDao: JournalDao,
    private val safetyPlanDao: SafetyPlanDao
) {
    // Mood Functions
    fun getAllMoods(): Flow<List<MoodEntity>> = moodDao.getAllMoods()
    suspend fun insertMood(mood: MoodEntity) = moodDao.insertMood(mood)

    // Journal Functions
    fun getAllJournals(): Flow<List<JournalEntity>> = journalDao.getAllJournals()
    suspend fun insertJournal(content: String, moodTag: String) {
        val encryptedContent = CryptoHelper.encrypt(content)
        val journal = JournalEntity(
            encryptedContent = encryptedContent,
            moodTag = moodTag,
            timestamp = System.currentTimeMillis()
        )
        journalDao.insertJournal(journal)
    }

    // Safety Plan Functions
    fun getSafetyPlan(): Flow<SafetyPlanEntity?> = safetyPlanDao.getSafetyPlan()
    suspend fun saveSafetyPlan(plan: SafetyPlanEntity) = safetyPlanDao.saveSafetyPlan(plan)
}