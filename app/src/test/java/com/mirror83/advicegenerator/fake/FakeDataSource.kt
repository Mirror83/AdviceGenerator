package com.mirror83.advicegenerator.fake

import com.mirror83.advicegenerator.network.Advice
import com.mirror83.advicegenerator.network.AdviceApiResponse

object FakeDataSource {
    val adviceSet = setOf(
        AdviceApiResponse(Advice(1, "Don't take things too personally")),
        AdviceApiResponse(Advice(2, "Ones before twos"))
    )
}