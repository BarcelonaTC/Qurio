package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.UserStatsDao
import com.barcelona.qurio.model.local.mapper.toModel
import com.barcelona.qurio.presentation.model.UserStats
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import javax.inject.Inject

class UserStatsRepositoryImpl @Inject constructor(
    private val dao: UserStatsDao
) : UserStatsRepository {
    override suspend fun getPreferences(): UserStats {
        return dao.getPreferences()?.toModel() ?: throw Exception("No preferences found")
    }

    override suspend fun updatePoints(points: Int) {
        dao.updatePoints(points)
    }

    override suspend fun updateLives(lives: Int) {
        dao.updateLives(lives)
    }

    override suspend fun increaseLives(amount: Int) {
        dao.increaseLives(amount)
    }

    override suspend fun decreaseLives(amount: Int) {
        dao.decreaseLives(amount)
    }

    override suspend fun updateRewards(rewards: Int) {
        dao.updateRewards(rewards)
    }

    override suspend fun saveLastLifeLostTime(timestamp: Long) {
        dao.updateLastLifeLostTime(timestamp)
    }

    override suspend fun getLastLifeLostTime(): Long {
        return dao.getLastLifeLostTime()
    }

    override suspend fun increasePoints(amount: Int) {
        dao.increasePoints(amount)
    }

    override suspend fun decreasePoints(amount: Int) {
        dao.decreasePoints(amount)
    }
}