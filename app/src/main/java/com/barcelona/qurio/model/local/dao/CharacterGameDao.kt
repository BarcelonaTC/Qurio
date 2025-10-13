package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barcelona.qurio.model.local.entity.CharacterGameEntity

@Dao
interface CharacterGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterGameEntity>)

    @Query("UPDATE characters_game SET isSelected = :isSelected WHERE id = :id")
    suspend fun updateCharacterSelection(id: Int, isSelected: Boolean)

    @Query("UPDATE characters_game SET isLocked = :isLocked WHERE id = :id")
    suspend fun updateCharacterLock(id: Int, isLocked: Boolean)

    @Query("SELECT * FROM characters_game WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterGameEntity

    @Query("SELECT * FROM characters_game")
    suspend fun getAllCharacters(): List<CharacterGameEntity>

    @Query("UPDATE characters_game SET isSelected = 0 WHERE isSelected = 1")
    suspend fun unselectAllCharacters()
}
