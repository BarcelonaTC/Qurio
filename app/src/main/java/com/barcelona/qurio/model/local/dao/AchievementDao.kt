package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barcelona.qurio.model.local.entity.AchievementEntity

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements:List<AchievementEntity>)

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getAchievementById(id: Int): AchievementEntity

    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievements(): List<AchievementEntity>

    @Query("UPDATE achievements SET isLocked = :isLocked WHERE id = :achievementId")
    suspend fun setAchievementLocking(achievementId: Int, isLocked: Boolean)
}
