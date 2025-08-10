package com.mirror83.advicegenerator.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.adviceslip.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AdviceGeneratorApiService {
    @GET("advice")
    suspend fun getAdvice(): String
}

object AdviceGeneratorApi {
    val retrofitService: AdviceGeneratorApiService by lazy {
        retrofit.create(AdviceGeneratorApiService::class.java)
    }
}