package com.example.serenespace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "safety_plan_table")
data class SafetyPlanEntity(
    @PrimaryKey val id: Int = 1, // We only ever save ONE safety plan per user
    val warningSigns: String = "",
    val copingStrategies: String = "",
    val supportContacts: String = ""
)