package com.barcelona.qurio.model.local.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity
import com.barcelona.qurio.presentation.model.TriviaGameSession
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun TriviaGameSessionEntity.toModel(): TriviaGameSession {
    return TriviaGameSession(
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        skippedAnswers = skippedAnswers,
        stars = stars,
        totalTimeSeconds = totalTimeSeconds,
        earnedCoins = earnedCoins,
        category = category,
        playedAt = date.toLocalDate(),
        streakAnswers = streakAnswers
    )
}

fun TriviaGameSession.toEntity(): TriviaGameSessionEntity {
    return TriviaGameSessionEntity(
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        skippedAnswers = skippedAnswers,
        stars = stars,
        totalTimeSeconds = totalTimeSeconds,
        earnedCoins = earnedCoins,
        category = category,
        streakAnswers = streakAnswers,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}