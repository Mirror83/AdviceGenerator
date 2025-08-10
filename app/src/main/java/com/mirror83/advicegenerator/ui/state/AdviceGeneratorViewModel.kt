package com.mirror83.advicegenerator.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val DEFAULT_QUOTE = "Don't take it personally."

class AdviceGeneratorViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AdviceGeneratorUiState(advice = DEFAULT_QUOTE))
    val uiState = _uiState.asStateFlow()

    fun getNextAdvice() {
        _uiState.update {
            currentState ->
            if (currentState.advice == DEFAULT_QUOTE) {
                currentState.copy(advice = "New advice")
            } else {
                currentState.copy(advice = DEFAULT_QUOTE)
            }
        }
    }

}