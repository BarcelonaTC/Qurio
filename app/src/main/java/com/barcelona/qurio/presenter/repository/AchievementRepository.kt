package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.Achievement

interface AchievementRepository {
    suspend fun getAllAchievements(): List<Achievement>
    suspend fun getAchievementById(id: Int): Achievement
    suspend fun setAchievementLocking(achievementId: Int, isLocked: Boolean)
}
