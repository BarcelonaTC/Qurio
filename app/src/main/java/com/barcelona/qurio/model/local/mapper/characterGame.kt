package com.barcelona.qurio.model.local.mapper

import com.barcelona.qurio.model.local.entity.CharacterGameEntity
import com.barcelona.qurio.presentation.model.CharacterGame

fun CharacterGameEntity.toModel(): CharacterGame {
    return CharacterGame(
        id = id,
        name = name,
        imageRes = imageRes,
        isLocked = isLocked,
        price = price,
        isSelected = isSelected,
        description = description,
        age = age,
        lockedImage = lockedImage
    )
}