package com.barcelona.qurio.presenter

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.service.UserStreakService
import jakarta.inject.Inject

class HomePresenter @Inject constructor(
    private val userStreakService: UserStreakService
) : BasePresenter<HomeView>() {
    fun getStreak() {
        tryToCall(
            block = { userStreakService.getStreakInfo() },
            onSuccess = { view?.showStreak(it) },
        )
    }
    fun updateStreak(){
        tryToCall(
            block = { userStreakService.updateStreak() },
        )
    }
}