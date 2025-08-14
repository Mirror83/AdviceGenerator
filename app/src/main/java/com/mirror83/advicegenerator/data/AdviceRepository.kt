package com.mirror83.advicegenerator.data

import com.mirror83.advicegenerator.network.Advice
import com.mirror83.advicegenerator.network.AdviceGeneratorApiService

interface AdviceRepository {
    suspend fun getAdvice(): Advice
}

class NetworkAdviceRepository(
    private val adviceGeneratorApiService: AdviceGeneratorApiService
) : AdviceRepository {
    override suspend fun getAdvice(): Advice {
        return adviceGeneratorApiService.getAdvice().advice
    }

}