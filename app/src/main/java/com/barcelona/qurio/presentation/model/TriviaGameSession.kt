package com.barcelona.qurio.presentation.model

data class TriviaGameSession(
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val stars: Int,
    val totalTimeSeconds: Int,
    val earnedCoins: Int,
) : java.io.Serializable