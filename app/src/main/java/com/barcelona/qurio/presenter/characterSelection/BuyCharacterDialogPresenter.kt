package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.BuyCharacterView
import com.barcelona.qurio.presenter.repository.CharacterRepository
import javax.inject.Inject

class BuyCharacterDialogPresenter @Inject constructor(
    private val repository: CharacterRepository
) : BasePresenter<BuyCharacterView>() {
    fun loadCharacter(characterId: Int) {
        tryToCall(
            block = {
                repository.getCharacterById(characterId)
            },
            onSuccess = {
                view?.showCharacter(it)
            },
        )
    }
}