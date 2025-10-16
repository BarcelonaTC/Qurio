package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.Question

interface TriviaGameRepository {
    suspend fun fetchQuestions(amount: Int, difficulty: String, type: String, category: Int): List<Question>
}