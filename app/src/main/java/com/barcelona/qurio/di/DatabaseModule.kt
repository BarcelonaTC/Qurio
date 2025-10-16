package com.barcelona.qurio.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.barcelona.qurio.model.local.CharacterDataSource
import com.barcelona.qurio.model.local.QurioDatabase
import com.barcelona.qurio.model.local.dao.CharacterGameDao
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.dao.UserStatsDao
import com.barcelona.qurio.model.local.dao.UserStreakDao
import com.barcelona.qurio.model.local.dao.VolumeLevelDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val database = Room.databaseBuilder(
                        context,
                        QurioDatabase::class.java,
                        "qurio_db"
                    ).build()

                    val dao = database.characterGameDao()
                    if (dao.getAllCharacters().isEmpty())
                        dao.insertCharacters(CharacterDataSource.baseCharacters)

                    val userStatsDao = database.userStatsDao()
                    userStatsDao.insertDefaultPreferences()
                }
            }
        }).build()
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
    fun provideGameSessionDao(database: QurioDatabase): CharacterGameDao {
        return database.characterGameDao()
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDao(database: QurioDatabase): UserStatsDao {
        return database.userStatsDao()
    }

    @Provides
    @Singleton
    fun provideVolumeLevelDao(database: QurioDatabase): VolumeLevelDao {
        return database.volumeLevelDao()
    }
}