package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_streak")
data class UserStreakEntity(
    @PrimaryKey
    val id: Int = 1,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: Long? = null,
    val monday: Boolean = false,
    val tuesday: Boolean = false,
    val wednesday: Boolean = false,
    val thursday: Boolean = false,
    val friday: Boolean = false,
    val saturday: Boolean = false,
    val sunday: Boolean = false,
    val weekStartDate: Long = 0L,
    val totalDaysActive: Int = 0,
)