package com.barcelona.qurio.presenter.achievement

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.repository.AchievementRepository
import javax.inject.Inject


class AchievementsPresenter @Inject constructor(
    private val achievementRepository: AchievementRepository,
) : BasePresenter<HomeView>() {

    fun getAllAchievements() {
        tryToCall(
            block = { achievementRepository.getAllAchievements() },
            onSuccess = { achievements ->
                view?.showAchievements(achievements)
            }
        )
    }

    fun getAchievement(achievementId: Int) {
        tryToCall(
            block = { achievementRepository.getAchievementById(achievementId) },
            onSuccess = { achievement ->
                view?.showCurrentAchievement(achievement)
            }
        )
    }

}