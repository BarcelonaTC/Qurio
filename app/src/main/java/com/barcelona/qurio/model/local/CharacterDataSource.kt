package com.barcelona.qurio.model.local

import com.barcelona.qurio.R
import com.barcelona.qurio.model.local.entity.CharacterGameEntity

object CharacterDataSource {
    val baseCharacters = listOf(
        CharacterGameEntity(
            name = "Rika",
            imageRes = R.drawable.rika,
            isLocked = false,
            price = 0,
            isSelected = true
        ),
        CharacterGameEntity(
            name = "Kaiyo",
            imageRes = R.drawable.character_kaiyo_open,
            isLocked = true,
            price = 300,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Mimi",
            imageRes = R.drawable.mimi_character,
            isLocked = true,
            price = 700,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Yoru",
            imageRes = R.drawable.yoru_character,
            isLocked = false,
            price = 1000,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Kuro",
            imageRes = R.drawable.kuro_character,
            isLocked = false,
            price = 3000,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Miko",
            imageRes = R.drawable.miko_character,
            isLocked = true,
            price = 7000,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Aori",
            imageRes = R.drawable.aori_character,
            isLocked = true,
            price = 12000,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Nara",
            imageRes = R.drawable.nara_character,
            isLocked = true,
            price = 30000,
            isSelected = false
        ),
        CharacterGameEntity(
            name = "Renji",
            imageRes = R.drawable.renji_character,
            isLocked = false,
            price = 50000,
            isSelected = false
        )
    )
}
