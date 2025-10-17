package com.barcelona.qurio.model.local.mapper

import com.barcelona.qurio.model.local.entity.AchievementEntity
import com.barcelona.qurio.presentation.model.Achievement

fun AchievementEntity.toModel(): Achievement{
    return Achievement(
        id = id,
        title = title,
        imageRes = imageRes,
        lockedImage = lockedImage,
        description = description,
        isLocked = isLocked
    )
}