package com.barcelona.qurio.presenter.characterSelection

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.BuyCharacterView
import com.barcelona.qurio.presenter.repository.CharacterRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import javax.inject.Inject

class BuyCharacterPresenter @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val userStatsRepository: UserStatsRepository
) : BasePresenter<BuyCharacterView>() {

    fun loadCharacter(characterId: Int) {
        tryToCall(
            block = {
                val character = characterRepository.getCharacterById(characterId)
                val totalPoints = userStatsRepository.getPreferences().points
                character to totalPoints
            },
            onSuccess = { (character, totalPoints) ->
                view?.showCharacter(character)
                view?.setBuyButtonEnabled(totalPoints >= character.price)
            },
        )
    }

    fun buyCharacter(characterId: Int) {
        tryToCall(
            block = {
                val character = characterRepository.getCharacterById(characterId)
                userStatsRepository.decreasePoints(character.price)
                characterRepository.unlockCharacter(character.id)
                characterRepository.selectCharacter(character.id)
                characterRepository.getCharacterById(character.id)
            },
            onSuccess = { updatedCharacter ->
                view?.onCharacterBought(updatedCharacter.id)
            },
        )
    }
}
