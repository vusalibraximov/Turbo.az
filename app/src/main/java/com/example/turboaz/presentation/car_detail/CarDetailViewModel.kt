package com.example.turboaz.presentation.car_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turboaz.domain.model.Car
import com.example.turboaz.domain.usecase.GetCarByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val getCarByIdUseCase: GetCarByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CarDetailState())
    val state: StateFlow<CarDetailState> = _state

    init {
        savedStateHandle.get<String>("carId")?.let { carId ->
            getCarDetails(carId)
        }
    }

    private fun getCarDetails(carId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            getCarByIdUseCase(carId)
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { car ->
                    _state.value = _state.value.copy(
                        car = car,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
}

data class CarDetailState(
    val car: Car? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
