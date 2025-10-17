package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.LevelType

interface DifficultyLevelView : BaseView {
    fun onConfirmClick(levelType: LevelType)
    fun onEasyClick()
    fun onMediumClick()
    fun onHardClick()
    fun setConfirmButtonEnabled(enable: Boolean)
    fun onCancelClick()
}