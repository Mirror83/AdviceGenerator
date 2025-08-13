package com.mirror83.advicegenerator.fake

import com.mirror83.advicegenerator.network.AdviceApiResponse
import com.mirror83.advicegenerator.network.AdviceGeneratorApiService

class FakeAdviceGeneratorApiService: AdviceGeneratorApiService {
    override suspend fun getAdvice(): AdviceApiResponse {
        return FakeDataSource.adviceSet.first()
    }
}