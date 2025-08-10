package com.mirror83.advicegenerator.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirror83.advicegenerator.network.AdviceGeneratorApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

const val DEFAULT_QUOTE = "Don't take it personally."

class AdviceGeneratorViewModel: ViewModel() {
    companion object {
        const val TAG = "AdviceGeneratorViewModel"
    }

    private val _uiState = MutableStateFlow(AdviceGeneratorUiState(advice = DEFAULT_QUOTE))
    val uiState = _uiState.asStateFlow()

    fun getNextAdvice() {
        viewModelScope.launch {
            try {
                val advice = AdviceGeneratorApi.retrofitService.getAdvice()
                Log.d(TAG, advice)
                _uiState.update {
                    currentState -> currentState.copy(advice = advice)
                }
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

}