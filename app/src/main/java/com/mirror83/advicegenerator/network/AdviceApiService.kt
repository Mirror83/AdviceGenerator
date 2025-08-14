package com.mirror83.advicegenerator.network

import retrofit2.http.GET

interface AdviceApiService {
    @GET("advice")
    suspend fun getAdvice(): AdviceApiResponse
}
