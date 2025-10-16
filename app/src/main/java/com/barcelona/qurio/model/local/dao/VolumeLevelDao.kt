package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import androidx.room.*
import com.barcelona.qurio.model.local.entity.VolumeEntity

@Dao
interface VolumeLevelDao {

    @Query("SELECT * FROM volume_level WHERE id = 1 LIMIT 1")
    suspend fun getVolumeLevels(): VolumeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: VolumeEntity)
}