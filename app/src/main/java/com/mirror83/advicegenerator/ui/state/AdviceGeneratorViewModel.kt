package com.mirror83.advicegenerator.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirror83.advicegenerator.data.AdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

//const val DEFAULT_QUOTE = "Don't take it personally."

@HiltViewModel
class AdviceGeneratorViewModel
    @Inject constructor(private val adviceRepository: AdviceRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow<AdviceGeneratorUiState>(AdviceGeneratorUiState.Loading)

    val uiState = _uiState.asStateFlow()

    fun getNextAdvice() {
        viewModelScope.launch {
            _uiState.update { _ -> AdviceGeneratorUiState.Loading }
            try {
                val advice = adviceRepository.getAdvice()
                _uiState.update { _ ->
                    AdviceGeneratorUiState.Success(advice)
                }
            } catch (_: IOException) {
                _uiState.update { _ -> AdviceGeneratorUiState.Error }
            }
        }
    }

    init {
        getNextAdvice()
    }

}