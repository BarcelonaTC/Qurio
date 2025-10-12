package com.barcelona.qurio.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.repository.TriviaGameRepositoryImpl
import com.barcelona.qurio.model.repository.TriviaGameSessionRepositoryImpl
import com.barcelona.qurio.presenter.OnBoardingPresenter
import com.barcelona.qurio.presenter.StartPlayPresenter
import com.barcelona.qurio.service.AppDatabase
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import com.barcelona.qurio.service.UserPreferences
import com.barcelona.qurio.service.UserPreferencesImpl
import com.barcelona.qurio.service.UserStreakDao
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
object AppModule {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    @Provides
    fun provideStartPlayPresenter(
        triviaGameRepository: TriviaGameRepository,
        triviaGameSessionRepository: TriviaGameSessionRepository
    ): StartPlayPresenter {
        return StartPlayPresenter(triviaGameRepository, triviaGameSessionRepository)
    }

    @Provides
    fun provideTriviaGameRepository(apiService: TriviaApiService): TriviaGameRepository {
        return TriviaGameRepositoryImpl(apiService)
    }

    @Provides
    fun provideOnBoardingPresenter(userPreferences: UserPreferences): OnBoardingPresenter {
        return OnBoardingPresenter(userPreferences)
    }

    @Provides
    fun provideContext(): Context = appContext

    @Provides
    fun provideUserPreferences(
        impl: UserPreferencesImpl
    ): UserPreferences = impl

    @Provides
    fun provideDataStore(): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    fun provideTriviaGameSessionRepository(dao: GameSessionDao): TriviaGameSessionRepository {
        return TriviaGameSessionRepositoryImpl(dao)
    }
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "qurio_db"
        ).build()
    }

    @Provides
    fun provideUserStreakDao(database: AppDatabase): UserStreakDao {
        return database.userStreakDao()
    }
}