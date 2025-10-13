package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.CharacterGame

interface CharacterSelectionView : BaseView {
    fun showCharacters(characters: List<CharacterGame>)
    fun showMessage(message: String)

    fun onClickOnCancelClick()

    fun onConfirmButtonClick()

    fun onCharacterClick(character: CharacterGame)
}