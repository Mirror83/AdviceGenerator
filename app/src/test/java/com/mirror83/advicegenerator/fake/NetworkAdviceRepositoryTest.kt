package com.mirror83.advicegenerator.fake

import com.mirror83.advicegenerator.data.NetworkAdviceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals

class NetworkAdviceRepositoryTest {
    @Test
    fun networkAdviceRepository_getAdvice_verifyAdvice() = runTest {
        val repository =
            NetworkAdviceRepository(adviceGeneratorApiService = FakeAdviceGeneratorApiService())
        assertEquals(
            FakeDataSource.adviceSet.first().advice,
            repository.getAdvice()
        )
    }
}