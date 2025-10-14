package com.barcelona.qurio.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_game")
data class CharacterGameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageRes: Int,
    val isLocked: Boolean,
    val lockedImage: Int,
    val price: Int,
    val isSelected: Boolean,
    val description: String,
    val age: String,
)