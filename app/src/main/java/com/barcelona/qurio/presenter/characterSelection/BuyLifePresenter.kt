package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.BuyLifeView
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStreakRepository
import javax.inject.Inject

class BuyLifePresenter @Inject constructor(
    private val userStreakRepository: UserStreakRepository,
    private val triviaGameSessionRepository: TriviaGameSessionRepository
) : BasePresenter<BuyLifeView>() {
    fun buyButtonEnable() {
        tryToCall(
            block = {
                triviaGameSessionRepository.getTotalPointsOfAllSessions()
            },
            onSuccess = { totalPoints ->
                view?.setBuyButtonEnabled(totalPoints >= 200)
            }
        )
    }

    fun onBuyClick() {
        tryToCall(
            block = {
                userStreakRepository.incrementLives()
            }
        )
    }
}