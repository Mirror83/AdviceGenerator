package com.mirror83.advicegenerator.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdviceApiResponse(
    @SerialName("slip")
    val advice: Advice
)

@Serializable
data class Advice(
    val id: Int,
    @SerialName("advice")
    val content: String
)