package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.LastGame

interface LastGamesView : BaseView {
    fun showLastGames(games: List<LastGame>)
}