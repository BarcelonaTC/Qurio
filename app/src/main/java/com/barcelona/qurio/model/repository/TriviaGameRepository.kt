package com.barcelona.qurio.model.repository

import com.barcelona.qurio.presentation.model.Question

interface TriviaGameRepository {
    suspend fun fetchQuestions(amount: Int, difficulty: String, type: String): List<Question>
}