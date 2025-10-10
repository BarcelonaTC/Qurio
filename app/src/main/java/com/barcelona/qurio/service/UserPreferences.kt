package com.barcelona.qurio.service

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val isFirstLaunch: Flow<Boolean>
    suspend fun setFirstLaunch()
}