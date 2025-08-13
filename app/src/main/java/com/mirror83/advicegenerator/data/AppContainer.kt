package com.mirror83.advicegenerator.data

import com.mirror83.advicegenerator.network.AdviceGeneratorApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue

interface AppContainer {
    val adviceRepository: AdviceRepository
}


class DefaultContainer: AppContainer {
    private val baseUrl = "https://api.adviceslip.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create OkHttpClient and add the interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(("application/json").toMediaType()))
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AdviceGeneratorApiService by lazy {
        retrofit.create(AdviceGeneratorApiService::class.java)
    }

    override val adviceRepository: AdviceRepository by lazy {
        NetworkAdviceRepository(retrofitService)
    }
}