package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.streak.StreakModel

interface UserStreakRepository {
    suspend fun updateStreak()
    suspend fun getStreakInfo(): StreakModel
    suspend fun getLivesCount(): Int
    suspend fun incrementLives()
    suspend fun decrementLives()
}
