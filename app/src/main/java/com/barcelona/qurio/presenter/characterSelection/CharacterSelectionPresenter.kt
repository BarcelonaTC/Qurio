package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.model.repository.CharacterRepository
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.view.CharacterSelectionView
import javax.inject.Inject

class CharacterSelectionPresenter @Inject constructor(
    private val characterRepository: CharacterRepository
) : BasePresenter<CharacterSelectionView>() {

    private var characters: List<CharacterGame> = emptyList()

    fun loadCharacters() {
        tryToCall(
            block = {
                val storedCharacters = characterRepository.getAllCharacters()
                val selected = storedCharacters.firstOrNull { it.isSelected }
                storedCharacters.map { it.copy(isSelected = it.name == selected?.name) }
            },
            onSuccess = {
                characters = it
                view?.showCharacters(it)
            }
        )
    }

    fun onCharacterClicked(character: CharacterGame) {
        if (character.isLocked) {
            view?.showMessage("${character.name} is locked!")
            return
        }

        tryToCall(
            block = {
                val all = characterRepository.getAllCharacters()
                all.forEach {
                    if (it.isSelected) characterRepository.selectCharacter(it.id)
                }
                characterRepository.selectCharacter(character.id)
                characterRepository.getAllCharacters()
            },
            onSuccess = {
                characters = it
                view?.showCharacters(it)
                view?.showMessage("Selected ${character.name}")
            }
        )
    }
}
