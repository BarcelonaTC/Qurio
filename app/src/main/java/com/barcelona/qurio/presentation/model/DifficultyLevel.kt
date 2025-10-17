package com.barcelona.qurio.presentation.model

data class DifficultyLevel(
    val levelType: LevelType,
)

enum class LevelType {
    EASY, MEDIUM, HARD
}