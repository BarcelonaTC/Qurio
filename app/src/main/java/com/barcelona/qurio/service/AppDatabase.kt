package com.barcelona.qurio.service

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserStreak::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userStreakDao(): UserStreakDao
}

