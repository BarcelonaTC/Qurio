package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView

interface OnBoardingView : BaseView {
    fun onRightArrowClicked()
    fun onLeftArrowClicked()
    fun onSwipeUp()
}