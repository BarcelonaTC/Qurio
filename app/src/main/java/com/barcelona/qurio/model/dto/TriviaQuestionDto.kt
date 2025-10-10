package com.barcelona.qurio.model.dto

import com.barcelona.qurio.presentation.model.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaQuestionDto(

    @SerialName("difficulty")
    val difficulty: String? = null,

    @SerialName("question")
    val question: String? = null,

    @SerialName("correct_answer")
    val correctAnswer: String? = null,

    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("category")
    val category: String? = null
)

fun TriviaQuestionDto.toDomain(): Question {
    return Question(
        category = category ?: "",
        type = type ?: "",
        difficulty = difficulty ?: "",
        question = question ?: "",
        correctAnswer = correctAnswer ?: "",
        incorrectAnswers = incorrectAnswers ?: emptyList()
    )
}