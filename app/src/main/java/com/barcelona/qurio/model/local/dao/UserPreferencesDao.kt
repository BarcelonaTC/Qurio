package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.barcelona.qurio.model.local.entity.UserPreferencesEntity

@Dao
interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences WHERE id = 1")
    suspend fun getPreferences(): UserPreferencesEntity?


    @Query("UPDATE user_preferences SET lives = :lives WHERE id = 1")
    suspend fun updateLives(lives: Int)

    @Query("UPDATE user_preferences SET lives = lives - :amount WHERE id = 1")
    suspend fun decreaseLives(amount: Int)

    @Query("UPDATE user_preferences SET lives = lives + :amount WHERE id = 1")
    suspend fun increaseLives(amount: Int)

    @Query("UPDATE user_preferences SET rewards = :rewards WHERE id = 1")
    suspend fun updateRewards(rewards: Int)

    @Query("UPDATE user_preferences SET points = :points WHERE id = 1")
    suspend fun updatePoints(points: Int)

    @Query("UPDATE user_preferences SET points = points - :amount WHERE id = 1")
    suspend fun decreasePoints(amount: Int)

    @Query("UPDATE user_preferences SET points = points + :amount WHERE id = 1")
    suspend fun increasePoints(amount: Int)
}