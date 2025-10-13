package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.CharacterGameDao
import com.barcelona.qurio.model.local.mapper.toModel
import com.barcelona.qurio.presentation.model.CharacterGame
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val characterDao: CharacterGameDao
) {
    suspend fun getAllCharacters(): List<CharacterGame> {
        return characterDao.getAllCharacters().map { it.toModel() }
    }

    suspend fun selectCharacter(id: Int) {
        characterDao.unselectAllCharacters()
        characterDao.updateCharacterSelection(id, true)
    }

    suspend fun unlockCharacter(id: Int) = characterDao.updateCharacterLock(id, false)
}
