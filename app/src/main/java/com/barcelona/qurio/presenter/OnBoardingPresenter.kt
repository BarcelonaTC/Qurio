package com.barcelona.qurio.presenter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.OnBoardingView
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface UserPreferences {
    val isFirstLaunch: Flow<Boolean>
    suspend fun setFirstLaunch()
}

class UserPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    override val isFirstLaunch: Flow<Boolean>
        get() {
            return dataStore.data.map {
                it[FIRST_LAUNCH] ?: true
            }
        }

    override suspend fun setFirstLaunch() {
        dataStore.edit {
            it[FIRST_LAUNCH] = false
        }
    }

    private companion object {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }
}

class OnBoardingPresenter @Inject constructor(
    private val userPreferences: UserPreferences
) : BasePresenter<OnBoardingView>() {
    fun setFirstLaunch() {
        tryToCall(
            block = { userPreferences.setFirstLaunch() },
        )
    }
}