package com.mirror83.advicegenerator.fake

import com.mirror83.advicegenerator.data.AdviceRepository
import com.mirror83.advicegenerator.network.Advice

class FakeAdviceRepository: AdviceRepository {
    override suspend fun getAdvice(): Advice {
        return FakeDataSource.adviceSet.first().advice
    }
}