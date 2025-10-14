package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.CharacterGame

interface BuyCharacterView : BaseView {
    fun showCharacter(character: CharacterGame)
    fun onBuyClick(characterId: Int)
    fun showMessage(message: String)
    fun setBuyButtonEnabled(enable: Boolean)
    fun onCancelClick()
}