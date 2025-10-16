package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_session")
data class TriviaGameSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val stars: Int,
    val totalTimeSeconds: Int,
    val earnedCoins: Int,
    val category: String,
    val date: Long = System.currentTimeMillis()
)