package com.barcelona.qurio.presentation.model.streak

data class StreakModel(
    val title: String,
    val subtitle: String,
    val days: List<DayStreak>
)