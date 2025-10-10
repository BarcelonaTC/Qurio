package com.barcelona.qurio.di

import android.content.Context
import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.model.repository.TriviaGameRepository
import com.barcelona.qurio.model.repository.TriviaGameRepositoryImpl
import com.barcelona.qurio.presenter.StartPlayPresenter
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    @Provides
    fun provideStartPlayPresenter(triviaGameRepository: TriviaGameRepository): StartPlayPresenter {
        return StartPlayPresenter(triviaGameRepository)
    }

    @Provides
    fun provideTriviaGameRepository(apiService: TriviaApiService): TriviaGameRepository {
        return TriviaGameRepositoryImpl(apiService)
    }
}