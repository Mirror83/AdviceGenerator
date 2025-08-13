package com.mirror83.advicegenerator.ui.state

import com.mirror83.advicegenerator.network.Advice


sealed interface AdviceGeneratorUiState {
    data class Success(val advice: Advice): AdviceGeneratorUiState
    object Error: AdviceGeneratorUiState
    object Loading: AdviceGeneratorUiState
}