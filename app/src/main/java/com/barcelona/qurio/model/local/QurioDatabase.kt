package com.barcelona.qurio.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity
import com.barcelona.qurio.model.local.dao.UserStreakDao
import com.barcelona.qurio.model.local.entity.UserStreakEntity

@Database(entities = [TriviaGameSessionEntity::class, UserStreakEntity::class], version = 1)
abstract class QurioDatabase : RoomDatabase() {
    abstract fun gameSessionDao(): GameSessionDao
    abstract fun userStreakDao(): UserStreakDao
}