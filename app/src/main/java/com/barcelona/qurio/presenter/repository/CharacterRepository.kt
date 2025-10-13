package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.CharacterGame

interface CharacterRepository {
    suspend fun getAllCharacters(): List<CharacterGame>
    suspend fun selectCharacter(id: Int)
    suspend fun getCharacterById(id: Int): CharacterGame
    suspend fun unlockCharacter(id: Int)
}
