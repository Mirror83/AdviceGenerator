package com.mirror83.advicegenerator.ui.state


sealed interface AdviceGeneratorUiState {
    data class Success(val advice: String): AdviceGeneratorUiState
    object Error: AdviceGeneratorUiState
    object Loading: AdviceGeneratorUiState
}