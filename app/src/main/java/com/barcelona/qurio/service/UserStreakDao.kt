package com.barcelona.qurio.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserStreakDao {
    @Query("SELECT * FROM user_streak WHERE id = 1")
    suspend fun getStreak(): UserStreak?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: UserStreak)

    @Update
    suspend fun updateStreak(streak: UserStreak)

    @Query("DELETE FROM user_streak")
    suspend fun deleteAll()
}