package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.UserStats

interface UserStatsRepository {
    suspend fun getPreferences(): UserStats
    suspend fun updatePoints(points: Int)
    suspend fun increasePoints(amount: Int)
    suspend fun decreasePoints(amount: Int)
    suspend fun updateLives(lives: Int)
    suspend fun increaseLives(amount: Int)
    suspend fun decreaseLives(amount: Int)
    suspend fun updateRewards(rewards: Int)
    suspend fun saveLastLifeLostTime(timestamp: Long)
    suspend fun getLastLifeLostTime(): Long
}