package com.barcelona.qurio.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.mapper.toLastGameModel
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.CharacterRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import com.barcelona.qurio.presenter.repository.UserStreakRepository
import jakarta.inject.Inject

class HomePresenter @Inject constructor(
    private val userStreakRepository: UserStreakRepository,
    private val characterRepository: CharacterRepository,
    private val userStatsRepository: UserStatsRepository
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
                userStreakRepository.updateStreak()
            },
        )
    }

    fun getTotalPoints() {
        tryToCall(
            block = { userStatsRepository.getPreferences().points },
            onSuccess = { view?.showTotalPoints(it) },
        )
    }

    fun getTotalLives() {
        tryToCall(
            block = { userStatsRepository.getPreferences().lives },
            onSuccess = { view?.showTotalLives(it) },
        )
    }

    fun getTotalRewards() {
        tryToCall(
            block = { userStatsRepository.getPreferences().rewards },
            onSuccess = { view?.showTotalRewards(it) },
        )
    }

    fun checkLivesBeforePlay(onHasLives: () -> Unit, onNoLives: () -> Unit) {
        tryToCall(
            block = { userStatsRepository.getPreferences().lives },
            onSuccess = { lives ->
                if (lives > 0) onHasLives() else onNoLives()
            }
        )
    }

    fun selectedCharacter() {
        tryToCall(
            block = { characterRepository.getSelectedCharacter() },
            onSuccess = {
                view?.showSelectedCharacter(it)
            },
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLastGames() {
        tryToCall(
            block = { triviaGameSessionRepository.getAllSessions() },
            onSuccess = { view?.showLastGames(it.map(TriviaGameSession::toLastGameModel)) },
        )
    }
}