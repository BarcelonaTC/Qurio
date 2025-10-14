package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.view.CharacterSelectionView
import com.barcelona.qurio.presenter.repository.CharacterRepository
import javax.inject.Inject

class CharacterSelectionPresenter @Inject constructor(
    private val characterRepository: CharacterRepository
) : BasePresenter<CharacterSelectionView>() {

    private var characters: List<CharacterGame> = emptyList()
    private lateinit var selectedCharacter: CharacterGame

    fun loadCharacters() {
        tryToCall(
            block = { characterRepository.getAllCharacters() },
            onSuccess = {
                characters = it
                view?.showCharacters(it)
            }
        )
    }

    fun onCharacterClicked(character: CharacterGame) {
        tryToCall(
            block = {
                characterRepository.selectCharacter(character.id)
                characterRepository.getAllCharacters()
            },
            onSuccess = {
                characters = it
                view?.showCharacters(it)
            }
        )
    }

    fun getSelectedCharacter() {
        tryToCall(
            block = { characterRepository.getSelectedCharacter() },
            onSuccess = {
                selectedCharacter = it
                view?.showSelectedCharacter(it)
            }
        )
    }
}
