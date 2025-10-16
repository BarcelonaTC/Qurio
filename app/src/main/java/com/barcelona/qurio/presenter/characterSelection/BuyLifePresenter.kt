package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.BuyLifeView
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import javax.inject.Inject

class BuyLifePresenter @Inject constructor(
    private val userStatsRepository: UserStatsRepository,
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
                userStatsRepository.increaseLives(1)
            }
        )
    }
}