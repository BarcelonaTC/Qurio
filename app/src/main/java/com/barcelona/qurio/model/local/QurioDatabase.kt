package com.barcelona.qurio.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity

@Database(entities = [TriviaGameSessionEntity::class], version = 1)
abstract class QurioDatabase : RoomDatabase() {
    abstract fun gameSessionDao(): GameSessionDao

}