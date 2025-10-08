package com.barcelona.qurio.presentation.model.gamecard

data class GameCardModel(
    val image:Int,
    val title: String,
    val borderColor: Int,
    val bottomGradient: Int,
    val borderWidth :Int = 5
)