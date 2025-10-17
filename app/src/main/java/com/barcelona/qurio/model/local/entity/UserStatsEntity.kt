package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStatsEntity (
    @PrimaryKey
    val id: Int = 1,
    val points: Int = 100,
    val lives:Int = 10,
    val rewards:Int = 0
)