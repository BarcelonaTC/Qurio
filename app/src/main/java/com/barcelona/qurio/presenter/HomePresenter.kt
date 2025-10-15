package com.barcelona.qurio.presenter

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStreakRepository
import jakarta.inject.Inject

class HomePresenter @Inject constructor(
    private val userStreakRepository: UserStreakRepository,
    private val triviaGameSessionRepository: TriviaGameSessionRepository
) : BasePresenter<HomeView>() {

    fun getStreak() {
        tryToCall(
            block = { userStreakRepository.getStreakInfo() },
            onSuccess = { view?.showStreak(it) },
        )
    }

    fun updateStreak() {
        tryToCall(
            block = {
                userStreakRepository.updateStreak() },
        )
    }

    fun getTotalPoints() {
        tryToCall(
            block = { triviaGameSessionRepository.getTotalPointsOfAllSessions() },
            onSuccess = { view?.showTotalPoints(it) },
        )
    }
    fun getTotalLives(){
        tryToCall(
            block = { userStreakRepository.getLivesCount() },
            onSuccess = { view?.showTotalLives(it) },
        )
    }
    fun checkLivesBeforePlay(onHasLives: () -> Unit, onNoLives: () -> Unit) {
        tryToCall(
            block = { userStreakRepository.getLivesCount() },
            onSuccess = { lives ->
                if (lives > 0) onHasLives() else onNoLives()
            }
        )
    }
}