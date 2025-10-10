package com.barcelona.qurio.presenter

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.OnBoardingView
import com.barcelona.qurio.service.UserPreferences
import jakarta.inject.Inject


class OnBoardingPresenter @Inject constructor(
    private val userPreferences: UserPreferences
) : BasePresenter<OnBoardingView>() {
    fun setFirstLaunch() {
        tryToCall(
            block = { userPreferences.setFirstLaunch() },
        )
    }
}