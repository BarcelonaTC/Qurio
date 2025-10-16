package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.CharacterGame

interface CharacterDetailView : BaseView {
    fun showCharacter(character: CharacterGame)
    fun onOkClick()
}
