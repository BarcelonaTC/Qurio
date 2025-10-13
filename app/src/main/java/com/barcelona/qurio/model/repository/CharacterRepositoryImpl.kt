package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.CharacterGameDao
import com.barcelona.qurio.model.local.mapper.toModel
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presenter.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterGameDao
) : CharacterRepository {
    override suspend fun getAllCharacters(): List<CharacterGame> {
        return characterDao.getAllCharacters().map { it.toModel() }
    }

    override suspend fun selectCharacter(id: Int) {
        characterDao.unselectAllCharacters()
        characterDao.updateCharacterSelection(id, true)
    }

    override suspend fun getCharacterById(id: Int): CharacterGame {
        return characterDao.getCharacterById(id).toModel()
    }

    override suspend fun unlockCharacter(id: Int) = characterDao.updateCharacterLock(id, false)
}
