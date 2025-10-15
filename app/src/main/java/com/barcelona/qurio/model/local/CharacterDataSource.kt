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
            isSelected = true,
            description = "Nature's little explorer! Rika talks to mushrooms and swears squirrels give her battle advice. Always ready for an adventure.",
            age = "12 Sunrooms",
            lockedImage = R.drawable.rika_without_background
        ),
        CharacterGameEntity(
            name = "Kaiyo",
            imageRes = R.drawable.character_kaiyo_open,
            isLocked = true,
            price = 300,
            isSelected = false,
            description = "A calm storm in human form. Kaiyo trains with ancient scrolls by day and drinks spicy tea by night. Sword sharp, heart sharper.",
            age = "4 Storms",
            lockedImage = R.drawable.kaiyo_without_background
        ),
        CharacterGameEntity(
            name = "Mimi",
            imageRes = R.drawable.mimi_character,
            isLocked = true,
            price = 700,
            isSelected = false,
            description = "A spirited troublemaker with a contagious laugh. Mimi dances through danger and turns chaos into art. Sharp-tongued but sharper-minded, she sees opportunity where others see obstacles.",
            age = "9 Sunrooms",
            lockedImage = R.drawable.mimi_without_background
        ),
        CharacterGameEntity(
            name = "Yoru",
            imageRes = R.drawable.yoru_character,
            isLocked = true,
            price = 1000,
            isSelected = false,
            description = "The night's quiet guardian. Yoru moves like shadows and speaks in whispers. Patient and observant, they protect the sleeping world with an unwavering commitment to justice.",
            age = "7 Storms",
            lockedImage = R.drawable.yoru_without_background
        ),
        CharacterGameEntity(
            name = "Kuro",
            imageRes = R.drawable.kuro_character,
            isLocked = false,
            price = 3000,
            isSelected = false,
            description = "A blazing inferno of passion and determination. Kuro fights with every fiber of their being and loves just as fiercely. Impulsive but pure of heart, they inspire others through sheer force of will.",
            age = "8 Storms",
            lockedImage = R.drawable.kuro_without_background
        ),
        CharacterGameEntity(
            name = "Miko",
            imageRes = R.drawable.miko_character,
            isLocked = true,
            price = 7000,
            isSelected = false,
            description = "A bridge between worlds, blessed with ancient wisdom. Miko speaks with nature's voice and heals with gentle hands. Small in stature but vast in spirit, she guides others through the fog of uncertainty.",
            age = "11 Sunrooms",
            lockedImage = R.drawable.miko_without_background
        ),
        CharacterGameEntity(
            name = "Aori",
            imageRes = R.drawable.aori_character,
            isLocked = true,
            price = 12000,
            isSelected = false,
            description = "The weathered veteran of countless trials. Aori's strategic mind and steady hand have led many through dark times. Serious and dependable, they carry the weight of leadership with quiet dignity.",
            age = "18 Storms",
            lockedImage = R.drawable.aori_without_background
        ),
        CharacterGameEntity(
            name = "Nara",
            imageRes = R.drawable.nara_character,
            isLocked = true,
            price = 30000,
            isSelected = false,
            description = "Dramatic, bold, and unapologetically confident. Nara commands attention with mere presence and speaks truth without filter. Beneath the theatrical flair lies a fiercely protective soul and unshakeable loyalty.",
            age = "10 Storms",
            lockedImage = R.drawable.nara_without_background
        ),
        CharacterGameEntity(
            name = "Renji",
            imageRes = R.drawable.renji_character,
            isLocked = true,
            price = 50000,
            isSelected = false,
            description = "Sunshine incarnate. Renji brings warmth and laughter wherever they go, melting even the coldest hearts. Young but wise beyond years, they prove that light always finds a way through the darkness.",
            age = "6 Sunrooms",
            lockedImage = R.drawable.renji_without_background
        )
    )
}