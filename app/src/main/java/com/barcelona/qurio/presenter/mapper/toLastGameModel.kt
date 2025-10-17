package com.barcelona.qurio.presenter.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.barcelona.qurio.presentation.model.LastGame
import com.barcelona.qurio.presentation.model.TriviaGameSession
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun TriviaGameSession.toLastGameModel(): LastGame {
    return LastGame(
        date = playedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        category = category.replace(Regex("\\r?\\n"), ""),
        coins = earnedCoins,
        stars = stars.toString(),
        durationInSeconds = secondsToTimeString(totalTimeSeconds)
    )
}

private fun secondsToTimeString(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return buildString {
        if (minutes > 0) append("${minutes}m ")
        append("${seconds}sec")
    }
}