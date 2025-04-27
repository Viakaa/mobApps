package com.example.homework_4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_4.model.NumberChecking
import com.example.homework_4.repository.NumberCheckingRepo
import com.example.homework_4.model.NumberCheckingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

sealed class UiState {
    object Loading : UiState()
    data class Success(val response: NumberCheckingResponse) : UiState()
    data class Error(val message: String) : UiState()
}

class NumberCheckingViewModel(private val repository: NumberCheckingRepo) : ViewModel() {

    private val _state = MutableStateFlow<UiState?>(null)
    val state: StateFlow<UiState?> = _state

    fun checkNumber(number: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val response: Response<NumberCheckingResponse> = repository.validatePhone(number)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    repository.saveValidation(
                        NumberChecking(
                            number = number,
                            valid = body.valid,
                            country = body.country,
                            location = body.location,
                            country_code = body.country_code
                        )
                    )
                    _state.value = UiState.Success(body)
                } else {
                    _state.value = UiState.Error("Validation error, buddy :/")
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.localizedMessage ?: "Hmm, unknown error...")
            }
        }
    }
}
