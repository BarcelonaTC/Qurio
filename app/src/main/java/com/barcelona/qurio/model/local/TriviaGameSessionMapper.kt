package com.barcelona.qurio.model.local

import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity
import com.barcelona.qurio.presentation.model.TriviaGameSession

fun TriviaGameSessionEntity.toModel(): TriviaGameSession{
    return TriviaGameSession(
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        skippedAnswers = skippedAnswers,
        stars = stars,
        totalTimeSeconds = totalTimeSeconds,
        earnedCoins = earnedCoins
    )
}
fun TriviaGameSession.toEntity(): TriviaGameSessionEntity{
    return TriviaGameSessionEntity(
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        skippedAnswers = skippedAnswers,
        stars = stars,
        totalTimeSeconds = totalTimeSeconds,
        earnedCoins = earnedCoins
    )
}