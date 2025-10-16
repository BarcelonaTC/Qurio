package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.model.dto.toDomain
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import java.net.UnknownHostException
import javax.inject.Inject

class TriviaGameRepositoryImpl @Inject constructor(
    private val triviaApiService: TriviaApiService
) : TriviaGameRepository {
    override suspend fun fetchQuestions(
        amount: Int,
        difficulty: String,
        type: String,
        category: Int
    ): List<Question> {
        return try {
            triviaApiService.getQuestions(
                amount = amount,
                difficulty = difficulty,
                type = type,
                category = category
            ).results
                ?.mapNotNull { it?.toDomain() }
                .orEmpty()
        } catch (_: UnknownHostException) {
            throw UnknownHostException("No internet connection")
        } catch (e: Exception) {
            throw e
        }
    }
}