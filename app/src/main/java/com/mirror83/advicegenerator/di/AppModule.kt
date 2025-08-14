package com.mirror83.advicegenerator.di

import com.mirror83.advicegenerator.data.AdviceRepository
import com.mirror83.advicegenerator.data.NetworkAdviceRepository
import com.mirror83.advicegenerator.network.AdviceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /** Create a singleton instance of AdviceRepository.
     * */
    @Provides
    @Singleton
    fun provideAdviceGeneratorApiService(): AdviceApiService {
        val baseUrl = "https://api.adviceslip.com"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Create OkHttpClient and add the interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                Json.asConverterFactory(("application/json").toMediaType())
            )
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()

        return retrofit.create(AdviceApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAdviceRepository(adviceApiService: AdviceApiService): AdviceRepository {
        return NetworkAdviceRepository(adviceApiService)
    }

}