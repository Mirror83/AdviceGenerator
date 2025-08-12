package com.mirror83.advicegenerator.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.adviceslip.com"

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

// Create OkHttpClient and add the interceptor
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(("application/json").toMediaType()))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

interface AdviceGeneratorApiService {
    @GET("advice")
    suspend fun getAdvice(): AdviceApiResponse
}

object AdviceGeneratorApi {
    val retrofitService: AdviceGeneratorApiService by lazy {
        retrofit.create(AdviceGeneratorApiService::class.java)
    }
}