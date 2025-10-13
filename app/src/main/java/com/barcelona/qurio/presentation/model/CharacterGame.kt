package com.barcelona.qurio.presentation.model

data class CharacterGame(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val isLocked: Boolean,
    val price: Int,
    var isSelected: Boolean
)
