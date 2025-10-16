package com.barcelona.qurio.presenter

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.repository.CharacterRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import com.barcelona.qurio.presenter.repository.UserStreakRepository
import com.barcelona.qurio.presenter.repository.VolumeLevelRepository
import jakarta.inject.Inject

class HomePresenter @Inject constructor(
    private val userStreakRepository: UserStreakRepository,
    private val characterRepository: CharacterRepository,
    private val userStatsRepository: UserStatsRepository
    private val triviaGameSessionRepository: TriviaGameSessionRepository,
    private val volumeLevelRepository: VolumeLevelRepository
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

    fun getMusicVolumeLevel() {
        tryToCall(
            block = { volumeLevelRepository.getMusicVolumeLevel() },
            onSuccess = { view?.setMusicVolumeLevel(it) }
        )

    }

    fun getSoundVolumeLevel() {
        tryToCall(
            block = { volumeLevelRepository.getMusicVolumeLevel() },
            onSuccess = { view?.setSoundVolumeLevel(it) }
        )
    }
}