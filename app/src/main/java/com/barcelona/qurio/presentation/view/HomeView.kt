package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.LastGame
import com.barcelona.qurio.presentation.model.streak.StreakModel

interface HomeView : BaseView {
    fun showStreak(streak: StreakModel)
    fun showTotalPoints(totalPoints: Int)
    fun showTotalLives(totalLives: Int)

    fun showLastGames(lastGames: List<LastGame>)
}
