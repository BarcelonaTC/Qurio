package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.AchievementDao
import com.barcelona.qurio.model.local.mapper.toModel
import com.barcelona.qurio.presentation.model.Achievement
import com.barcelona.qurio.presenter.repository.AchievementRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
): AchievementRepository {
    override suspend fun getAllAchievements(): List<Achievement> {
        return achievementDao.getAllAchievements().map { it.toModel() }
    }

    override suspend fun getAchievementById(id: Int): Achievement {
        return achievementDao.getAchievementById(id).toModel()
    }

    override suspend fun setAchievementLocking(
        achievementId: Int,
        isLocked: Boolean
    ) {
        achievementDao.setAchievementLocking(achievementId, isLocked)
    }
}