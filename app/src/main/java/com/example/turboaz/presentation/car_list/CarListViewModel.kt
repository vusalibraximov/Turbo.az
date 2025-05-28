package com.example.turboaz.presentation.car_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turboaz.domain.model.Car
import com.example.turboaz.domain.usecase.FilterCarsUseCase
import com.example.turboaz.domain.usecase.GetCarsUseCase
import com.example.turboaz.domain.usecase.SearchCarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val searchCarsUseCase: SearchCarsUseCase,
    private val filterCarsUseCase: FilterCarsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CarListState())
    val state: StateFlow<CarListState> = _state

    init {
        getCars()
    }

    fun onEvent(event: CarListEvent) {
        when (event) {
            is CarListEvent.Refresh -> getCars()
            is CarListEvent.Search -> searchCars(event.query)
            is CarListEvent.Filter -> filterCars(event.filters)
        }
    }

    private fun getCars() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            getCarsUseCase()
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { cars ->
                    _state.value = _state.value.copy(
                        cars = cars,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    private fun searchCars(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            searchCarsUseCase(query)
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { cars ->
                    _state.value = _state.value.copy(
                        cars = cars,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    private fun filterCars(filters: CarFilters) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            filterCarsUseCase(
                minPrice = filters.minPrice,
                maxPrice = filters.maxPrice,
                brand = filters.brand,
                model = filters.model,
                minYear = filters.minYear,
                maxYear = filters.maxYear,
                fuelType = filters.fuelType,
                transmission = filters.transmission
            )
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { cars ->
                    _state.value = _state.value.copy(
                        cars = cars,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
}

data class CarListState(
    val cars: List<Car> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class CarListEvent {
    object Refresh : CarListEvent()
    data class Search(val query: String) : CarListEvent()
    data class Filter(val filters: CarFilters) : CarListEvent()
}

data class CarFilters(
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val brand: String? = null,
    val model: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val fuelType: String? = null,
    val transmission: String? = null
)
