package com.barcelona.qurio.presentation.model

data class CharacterGame(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val isLocked: Boolean,
    val lockedImage: Int,
    val price: Int,
    var isSelected: Boolean,
    val description: String,
    val age: String
)
