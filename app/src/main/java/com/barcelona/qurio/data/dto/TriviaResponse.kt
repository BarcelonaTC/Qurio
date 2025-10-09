package com.barcelona.qurio.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponse(

    @SerialName("response_code")
    val responseCode: Int? = null,

    @SerialName("results")
    val results: List<TriviaQuestionDto?>? = null
)