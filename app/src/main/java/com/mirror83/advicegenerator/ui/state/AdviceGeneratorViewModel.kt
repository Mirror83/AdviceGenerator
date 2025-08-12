package com.mirror83.advicegenerator.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirror83.advicegenerator.network.AdviceApiResponse
import com.mirror83.advicegenerator.network.AdviceGeneratorApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

//const val DEFAULT_QUOTE = "Don't take it personally."

class AdviceGeneratorViewModel : ViewModel() {
    companion object {
        const val TAG = "AdviceGeneratorViewModel"
    }

    private val _uiState =
        MutableStateFlow<AdviceGeneratorUiState>(AdviceGeneratorUiState.Loading)

    val uiState = _uiState.asStateFlow()

    fun getNextAdvice() {
        viewModelScope.launch {
            _uiState.update { _ -> AdviceGeneratorUiState.Loading }

            try {
                val response: AdviceApiResponse = AdviceGeneratorApi.retrofitService.getAdvice()
                Log.d(TAG, response.toString())
                _uiState.update { _ ->
                    AdviceGeneratorUiState.Success(response.advice.content)
                }
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
                _uiState.update { _ -> AdviceGeneratorUiState.Error }
            }
        }
    }

    init {
        getNextAdvice()
    }

}