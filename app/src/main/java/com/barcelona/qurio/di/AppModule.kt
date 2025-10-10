package com.barcelona.qurio.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.presenter.OnBoardingPresenter
import com.barcelona.qurio.presenter.StartPlayPresenter
import com.barcelona.qurio.presenter.UserPreferences
import com.barcelona.qurio.presenter.UserPreferencesImpl
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
    fun provideStartPlayPresenter(api: TriviaApiService): StartPlayPresenter {
        return StartPlayPresenter(api)
    }

    @Provides
    fun provideOnBoardingPresenter(userPreferences: UserPreferences): OnBoardingPresenter {
        return OnBoardingPresenter(userPreferences)
    }

    @Provides
    fun provideUserPreferences(
        impl: UserPreferencesImpl
    ): UserPreferences = impl

    @Provides
    fun provideDataStore(): DataStore<Preferences> {
        return appContext.dataStore
    }
}
