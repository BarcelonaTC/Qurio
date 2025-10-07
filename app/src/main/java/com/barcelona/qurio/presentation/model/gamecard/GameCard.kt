package com.barcelona.qurio.presentation.model.gamecard

data class GameCard(
    val cardImage:Int,
    val cardTitle: String,
    val cardBorderColor: Int,
    val cardBottomGradient: Int,
    val cardBorderWidth :Int = 5
)