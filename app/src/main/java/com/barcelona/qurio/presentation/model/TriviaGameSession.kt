package com.barcelona.qurio.presentation.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class TriviaGameSession(
    val correctAnswers: Int,
    val streakAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val stars: Int,
    val totalTimeSeconds: Int,
    val earnedCoins: Int,
    val category: String,
    val playedAt: LocalDate = LocalDate.now()
) : java.io.Serializable