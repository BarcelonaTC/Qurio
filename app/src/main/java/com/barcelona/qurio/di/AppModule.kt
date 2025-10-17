package com.barcelona.qurio.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.model.local.dao.AchievementDao
import com.barcelona.qurio.model.local.dao.CharacterGameDao
import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.dao.UserStatsDao
import com.barcelona.qurio.model.local.dao.UserStreakDao
import com.barcelona.qurio.model.local.dao.VolumeLevelDao
import com.barcelona.qurio.model.repository.AchievementRepositoryImpl
import com.barcelona.qurio.model.repository.CharacterRepositoryImpl
import com.barcelona.qurio.model.repository.TriviaGameRepositoryImpl
import com.barcelona.qurio.model.repository.TriviaGameSessionRepositoryImpl
import com.barcelona.qurio.model.repository.UserPreferencesImpl
import com.barcelona.qurio.model.repository.UserStatsRepositoryImpl
import com.barcelona.qurio.model.repository.UserStreakRepositoryImpl
import com.barcelona.qurio.model.repository.VolumeLevelRepositoryImpl
import com.barcelona.qurio.presenter.LastGamesPresenter
import com.barcelona.qurio.presenter.OnBoardingPresenter
import com.barcelona.qurio.presenter.StartPlayPresenter
import com.barcelona.qurio.presenter.achievement.AchievementsPresenter
import com.barcelona.qurio.presenter.characterSelection.BuyCharacterPresenter
import com.barcelona.qurio.presenter.characterSelection.CharacterSelectionPresenter
import com.barcelona.qurio.presenter.repository.AchievementRepository
import com.barcelona.qurio.presenter.repository.CharacterRepository
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserPreferences
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import com.barcelona.qurio.presenter.repository.UserStreakRepository
import com.barcelona.qurio.presenter.repository.VolumeLevelRepository
import dagger.Module
import dagger.Provides

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
        triviaGameSessionRepository: TriviaGameSessionRepository,
        userStatsRepository: UserStatsRepository,
        volumeLevelRepository: VolumeLevelRepository,
    ): StartPlayPresenter {
        return StartPlayPresenter(
            triviaGameRepository,
            triviaGameSessionRepository,
            userStatsRepository,
            volumeLevelRepository
        )
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
    fun provideUserStreakRepository(dao: UserStreakDao): UserStreakRepository {
        return UserStreakRepositoryImpl(dao)
    }


    @Provides
    fun provideCharacterRepository(
        dao: CharacterGameDao
    ): CharacterRepository = CharacterRepositoryImpl(dao)


    @Provides
    fun provideCharacterSelectionPresenter(
        characterRepository: CharacterRepository
    ): CharacterSelectionPresenter {
        return CharacterSelectionPresenter(characterRepository)
    }

    @Provides
    fun provideBuyCharacterPresenter(
        characterRepository: CharacterRepository,
        userStatsRepository: UserStatsRepository
    ): BuyCharacterPresenter {
        return BuyCharacterPresenter(
            characterRepository,
            userStatsRepository
        )
    }

    @Provides
    fun provideUserStatsRepository(dao: UserStatsDao): UserStatsRepository {
        return UserStatsRepositoryImpl(dao)
    }

    @Provides
    fun provideLastGamesPresenter(
        triviaGameSessionRepository: TriviaGameSessionRepository
    ): LastGamesPresenter {
        return LastGamesPresenter(triviaGameSessionRepository)
    }

    @Provides
    fun provideVolumeLevelRepository(dao: VolumeLevelDao): VolumeLevelRepository {
        return VolumeLevelRepositoryImpl(dao)
    }

    @Provides
    fun provideAchievementRepository(
        achievementDao: AchievementDao
    ): AchievementRepository {
        return AchievementRepositoryImpl(
            achievementDao = achievementDao
        )
    }

    @Provides
    fun provideAchievementPresenter(
        achievementRepository: AchievementRepository,
        triviaGameSessionRepository: TriviaGameSessionRepository,
        userStatsRepository: UserStatsRepository
    ): AchievementsPresenter {
        return AchievementsPresenter(
            achievementRepository,
            triviaGameSessionRepository,
            userStatsRepository
        )
    }

}