package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.model.repository.CharacterRepositoryImpl
import com.barcelona.qurio.presentation.view.CharacterDetailView
import javax.inject.Inject

class CharacterDetailPresenter @Inject constructor(
    private val repository: CharacterRepositoryImpl
) : BasePresenter<CharacterDetailView>() {
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
