package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.barcelona.qurio.model.local.entity.UserStreakEntity

@Dao
interface UserStreakDao {
    @Query("SELECT * FROM user_streak WHERE id = 1")
    suspend fun getStreak(): UserStreakEntity?


    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertStreak(streak: UserStreakEntity)

    @Update
    suspend fun updateStreak(streak: UserStreakEntity)

    @Query("DELETE FROM user_streak")
    suspend fun deleteAll()

    @Query("SELECT lives FROM user_streak WHERE id = 1")
    suspend fun getLivesCount(): Int

    @Query("UPDATE user_streak SET lives = lives + 1 WHERE id = 1")
    suspend fun incrementLives()

    @Query("UPDATE user_streak SET lives = CASE WHEN lives > 0 THEN lives - 1 ELSE 0 END WHERE id = 1")
    suspend fun decrementLives()
}