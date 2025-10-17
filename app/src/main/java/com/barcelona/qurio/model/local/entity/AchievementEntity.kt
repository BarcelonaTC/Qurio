package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val imageRes: Int,
    val lockedImage: Int,
    val isLocked: Boolean,
    val description: String,
)