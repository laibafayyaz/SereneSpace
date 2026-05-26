package com.example.serenespace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.serenespace.CryptoHelper
import com.example.serenespace.data.MoodEntity
import com.example.serenespace.data.SafetyPlanEntity
import com.example.serenespace.repository.MoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {

    val moodHistory: StateFlow<List<MoodEntity>> = repository.getAllMoods()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveMood(emoji: String, moodType: String, note: String) {
        viewModelScope.launch {
            repository.insertMood(MoodEntity(emoji = emoji, moodType = moodType, note = note, timestamp = System.currentTimeMillis()))
        }
    }

    val journalHistory: StateFlow<List<DecryptedJournal>> = repository.getAllJournals()
        .map { journals -> journals.map { DecryptedJournal(it.id, CryptoHelper.decrypt(it.encryptedContent), it.moodTag, it.timestamp) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveJournal(content: String, moodTag: String) {
        viewModelScope.launch { repository.insertJournal(content, moodTag) }
    }

    val safetyPlan: StateFlow<SafetyPlanEntity?> = repository.getSafetyPlan()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveSafetyPlan(warningSigns: String, copingStrategies: String, supportContacts: String) {
        viewModelScope.launch {
            repository.saveSafetyPlan(
                SafetyPlanEntity(
                    warningSigns = warningSigns,
                    copingStrategies = copingStrategies,
                    supportContacts = supportContacts
                )
            )
        }
    }
}

data class DecryptedJournal(val id: Int, val content: String, val moodTag: String, val timestamp: Long)

class MoodViewModelFactory(private val repository: MoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}