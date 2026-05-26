package com.example.serenespace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SafetyPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replaces the old plan when saved
    suspend fun saveSafetyPlan(plan: SafetyPlanEntity)

    @Query("SELECT * FROM safety_plan_table WHERE id = 1")
    fun getSafetyPlan(): Flow<SafetyPlanEntity?>
}