package com.barcelona.qurio.presentation.model

import androidx.annotation.DrawableRes

data class Achievement(
    val id: Int=1,
    @param:DrawableRes val imageRes: Int,
    @param:DrawableRes val lockedImage: Int,
    val title: String,
    val description: String,
    val isLocked: Boolean = true
)
