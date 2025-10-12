package com.barcelona.qurio.model.dto

import com.barcelona.qurio.R
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel

val gameCards = listOf(
    GameCardModel(
        image = R.drawable.music,
        title = "Music",
        borderColor = R.color.secondary,
        bottomGradient = R.drawable.music_film_tv_gamecard_gradient,
        categoryId = 12
    ),
    GameCardModel(
        image = R.drawable.food_drink,
        title = "Food & Drink",
        borderColor = R.color.primary,
        bottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient,
        categoryId = 9
    ),
    GameCardModel(
        image = R.drawable.geography,
        title = "Geography",
        borderColor = R.color.green,
        bottomGradient = R.drawable.geography_science_gamecard_gradient,
        categoryId = 22
    ),
    GameCardModel(
        image = R.drawable.general_knowledge,
        title = "General \nKnowledge",
        borderColor = R.color.orange,
        bottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient,
        categoryId = 9
    ),
    GameCardModel(
        image = R.drawable.film_tv,
        title = "Film & TV",
        borderColor = R.color.secondary,
        bottomGradient = R.drawable.music_film_tv_gamecard_gradient,
        categoryId = 11
    ),
    GameCardModel(
        image = R.drawable.society_culture,
        title = "Society & Culture",
        borderColor = R.color.orange,
        bottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient,
        categoryId = 9
    ),
    GameCardModel(
        image = R.drawable.science,
        title = "Science",
        borderColor = R.color.green,
        bottomGradient = R.drawable.geography_science_gamecard_gradient,
        categoryId = 17
    ),
    GameCardModel(
        image = R.drawable.sport,
        title = "Sport",
        borderColor = R.color.primary,
        bottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient,
        categoryId = 21
    ),
    GameCardModel(
        image = R.drawable.arts,
        title = "Arts",
        borderColor = R.color.primary,
        bottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient,
        categoryId = 25
    ),
    GameCardModel(
        image = R.drawable.history,
        title = "History",
        borderColor = R.color.orange,
        bottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient,
        categoryId = 23
    )
)