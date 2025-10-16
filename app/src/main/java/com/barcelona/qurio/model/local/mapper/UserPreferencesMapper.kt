package com.barcelona.qurio.model.local.mapper

import com.barcelona.qurio.model.local.entity.UserPreferencesEntity
import com.barcelona.qurio.presentation.model.UserPreferences

fun UserPreferencesEntity.toModel(): UserPreferences{
    return UserPreferences(
        points = points,
        lives = lives,
        rewards = rewards
    )
}
fun UserPreferences.toEntity(): UserPreferencesEntity{
    return UserPreferencesEntity(
        points = points,
        lives = lives,
        rewards = rewards
    )
}