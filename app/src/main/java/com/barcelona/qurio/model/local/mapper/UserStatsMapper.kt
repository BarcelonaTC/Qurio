package com.barcelona.qurio.model.local.mapper

import com.barcelona.qurio.model.local.entity.UserStatsEntity
import com.barcelona.qurio.presentation.model.UserStats

fun UserStatsEntity.toModel(): UserStats{
    return UserStats(
        points = points,
        lives = lives,
        rewards = rewards
    )
}
fun UserStats.toEntity(): UserStatsEntity{
    return UserStatsEntity(
        points = points,
        lives = lives,
        rewards = rewards
    )
}