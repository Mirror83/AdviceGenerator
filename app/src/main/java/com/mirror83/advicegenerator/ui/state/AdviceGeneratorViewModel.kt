package com.mirror83.advicegenerator.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mirror83.advicegenerator.AdviceGeneratorApplication
import com.mirror83.advicegenerator.data.AdviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

//const val DEFAULT_QUOTE = "Don't take it personally."

class AdviceGeneratorViewModel(private val adviceRepository: AdviceRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow<AdviceGeneratorUiState>(AdviceGeneratorUiState.Loading)

    val uiState = _uiState.asStateFlow()

    fun getNextAdvice() {
        viewModelScope.launch {
            _uiState.update { _ -> AdviceGeneratorUiState.Loading }

            try {
                val advice = adviceRepository.getAdvice()
                Log.d(TAG, advice.toString())
                _uiState.update { _ ->
                    AdviceGeneratorUiState.Success(advice)
                }
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
                _uiState.update { _ -> AdviceGeneratorUiState.Error }
            }
        }
    }

    companion object {
        const val TAG = "AdviceGeneratorViewModel"
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AdviceGeneratorApplication)
                val adviceRepository = application.container.adviceRepository
                AdviceGeneratorViewModel(adviceRepository = adviceRepository)
            }
        }
    }

    init {
        getNextAdvice()
    }

}