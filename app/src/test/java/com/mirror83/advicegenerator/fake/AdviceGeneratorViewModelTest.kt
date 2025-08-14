package com.mirror83.advicegenerator.fake

import com.mirror83.advicegenerator.rules.TestDispatcherRule
import com.mirror83.advicegenerator.ui.state.AdviceGeneratorUiState
import com.mirror83.advicegenerator.ui.state.AdviceGeneratorViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AdviceGeneratorViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun adviceGeneratorViewModel_getNextAdviceInInit_uiStateIsSuccess() = runTest {
        val adviceGeneratorViewModel = AdviceGeneratorViewModel(
            adviceRepository = FakeAdviceRepository()
        )

        val adviceGeneratorUiState = adviceGeneratorViewModel.uiState.value

        assertEquals(
            AdviceGeneratorUiState.Success(
                FakeDataSource.adviceSet.first().advice
            ),
            adviceGeneratorUiState
        )

    }
}