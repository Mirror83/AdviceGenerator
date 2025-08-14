package com.mirror83.advicegenerator.data

import com.mirror83.advicegenerator.network.Advice
import com.mirror83.advicegenerator.network.AdviceApiService

interface AdviceRepository {
    suspend fun getAdvice(): Advice
}

class NetworkAdviceRepository(
    private val adviceApiService: AdviceApiService
) : AdviceRepository {
    override suspend fun getAdvice(): Advice {
        return adviceApiService.getAdvice().advice
    }

}