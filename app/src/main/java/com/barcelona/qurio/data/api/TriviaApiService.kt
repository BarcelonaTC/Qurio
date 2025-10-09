package com.barcelona.qurio.data.api

import com.barcelona.qurio.data.dto.TriviaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String? = null,
        @Query("encode") encode: String? = null,
        @Query("token") token: String? = null
    ): TriviaResponse
}