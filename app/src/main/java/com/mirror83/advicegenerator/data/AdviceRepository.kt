package com.mirror83.advicegenerator.data

import com.mirror83.advicegenerator.network.Advice
import com.mirror83.advicegenerator.network.AdviceGeneratorApi

interface AdviceRepository {
    suspend fun getAdvice(): Advice
}

class NetworkAdviceRepository(): AdviceRepository {
    override suspend fun getAdvice(): Advice {
        return AdviceGeneratorApi.retrofitService.getAdvice().advice
    }

}