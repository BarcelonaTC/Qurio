package com.barcelona.qurio.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.view.LastGamesView
import com.barcelona.qurio.presenter.mapper.toLastGameModel
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository

class LastGamesPresenter(
    private val triviaGameSessionRepository: TriviaGameSessionRepository
) : BasePresenter<LastGamesView>() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLastGames() {
        tryToCall(
            block = { triviaGameSessionRepository.getAllSessions() },
            onSuccess = { view?.showLastGames(it.map(TriviaGameSession::toLastGameModel)) },
        )
    }
}
