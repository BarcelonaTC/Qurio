package com.barcelona.qurio.di

import android.content.Context
import androidx.room.Room
import com.barcelona.qurio.model.local.QurioDatabase
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.dao.UserStreakDao
import com.barcelona.qurio.model.local.dao.VolumeLevelDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): QurioDatabase {
        return Room.databaseBuilder(
            context,
            QurioDatabase::class.java,
            "qurio_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: QurioDatabase): GameSessionDao {
        return db.gameSessionDao()
    }

    @Provides
    @Singleton
    fun provideUserStreakDao(database: QurioDatabase): UserStreakDao {
        return database.userStreakDao()
    }

    @Provides
    @Singleton
    fun provideVolumeLevelDao(database: QurioDatabase): VolumeLevelDao {
        return database.volumeLevelDao()
    }
}