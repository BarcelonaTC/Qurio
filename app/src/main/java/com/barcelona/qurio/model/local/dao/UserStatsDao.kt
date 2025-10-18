package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.barcelona.qurio.model.local.entity.UserStatsEntity

@Dao
interface UserStatsDao {
    @Query("SELECT * FROM user_stats WHERE id = 1")
    suspend fun getPreferences(): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(preferences: UserStatsEntity)

    @Transaction
    suspend fun insertDefaultPreferences() {
        insert(UserStatsEntity())
    }

    @Query("UPDATE user_stats SET lives = :lives WHERE id = 1")
    suspend fun updateLives(lives: Int)

    @Query("UPDATE user_stats SET lives = lives - :amount WHERE id = 1")
    suspend fun decreaseLives(amount: Int)

    @Query("UPDATE user_stats SET lives = lives + :amount WHERE id = 1")
    suspend fun increaseLives(amount: Int)

    @Query("UPDATE user_stats SET rewards = :rewards WHERE id = 1")
    suspend fun updateRewards(rewards: Int)

    @Query("UPDATE user_stats SET points = :points WHERE id = 1")
    suspend fun updatePoints(points: Int)

    @Query("UPDATE user_stats SET points = points - :amount WHERE id = 1")
    suspend fun decreasePoints(amount: Int)

    @Query("UPDATE user_stats SET points = points + :amount WHERE id = 1")
    suspend fun increasePoints(amount: Int)

    @Query("UPDATE user_stats SET lastLifeLostTime = :timestamp WHERE id = 1")
    suspend fun updateLastLifeLostTime(timestamp: Long)

    @Query("SELECT lastLifeLostTime FROM user_stats WHERE id = 1")
    suspend fun getLastLifeLostTime(): Long
}