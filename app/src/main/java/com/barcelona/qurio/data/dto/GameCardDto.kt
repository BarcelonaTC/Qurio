package com.barcelona.qurio.data.dto

import com.barcelona.qurio.R
import com.barcelona.qurio.domain.entity.GameCard

val GameCardList = listOf(
    GameCard(
        cardImage = R.drawable.music,
        cardTitle = "Music",
        cardBorderColor = R.color.secondary,
        cardBottomGradient = R.drawable.music_film_tv_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.food_drink,
        cardTitle = "Food & Drink",
        cardBorderColor = R.color.primary,
        cardBottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.geography,
        cardTitle = "Geography",
        cardBorderColor = R.color.green,
        cardBottomGradient = R.drawable.geography_science_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.general_knowledge,
        cardTitle = "General \nKnowledge",
        cardBorderColor = R.color.orange,
        cardBottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.film_tv,
        cardTitle = "Film & TV",
        cardBorderColor = R.color.secondary,
        cardBottomGradient = R.drawable.music_film_tv_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.society_culture,
        cardTitle = "Society & Culture",
        cardBorderColor = R.color.orange,
        cardBottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.science,
        cardTitle = "Science",
        cardBorderColor = R.color.green,
        cardBottomGradient = R.drawable.geography_science_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.sport,
        cardTitle = "Sport",
        cardBorderColor = R.color.primary,
        cardBottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.arts,
        cardTitle = "Arts",
        cardBorderColor = R.color.primary,
        cardBottomGradient = R.drawable.sport_food_drink_arts_gamecard_gradient
    ),
    GameCard(
        cardImage = R.drawable.history,
        cardTitle = "History",
        cardBorderColor = R.color.orange,
        cardBottomGradient = R.drawable.general_knowledge_history_culture_gamecard_gradient
    ),
)