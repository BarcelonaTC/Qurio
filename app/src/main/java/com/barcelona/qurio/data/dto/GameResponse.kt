package com.barcelona.qurio.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(

    @SerialName("response_code")
    val responseCode: Int? = null,

    @SerialName("results")
    val results: List<GameDto?>? = null
)