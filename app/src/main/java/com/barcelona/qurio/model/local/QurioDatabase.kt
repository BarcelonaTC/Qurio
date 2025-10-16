package com.barcelona.qurio.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barcelona.qurio.model.local.dao.CharacterGameDao
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.dao.UserStatsDao
import com.barcelona.qurio.model.local.dao.UserStreakDao
import com.barcelona.qurio.model.local.entity.CharacterGameEntity
import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity
import com.barcelona.qurio.model.local.dao.VolumeLevelDao
import com.barcelona.qurio.model.local.entity.UserStatsEntity
import com.barcelona.qurio.model.local.entity.UserStreakEntity
import com.barcelona.qurio.model.local.entity.VolumeEntity

@Database(
    entities = [TriviaGameSessionEntity::class, UserStreakEntity::class, CharacterGameEntity::class, UserStatsEntity::class, VolumeEntity::class],
    version = 1
)
abstract class QurioDatabase : RoomDatabase() {
    abstract fun gameSessionDao(): GameSessionDao
    abstract fun userStreakDao(): UserStreakDao
    abstract fun characterGameDao(): CharacterGameDao
    abstract fun userStatsDao(): UserStatsDao
    abstract fun volumeLevelDao(): VolumeLevelDao
}