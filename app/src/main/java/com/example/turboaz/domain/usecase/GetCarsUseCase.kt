package com.example.turboaz.domain.usecase

import com.example.turboaz.domain.model.Car
import com.example.turboaz.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(): Flow<List<Car>> {
        return repository.getCars()
    }
}

class GetCarByIdUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(id: String): Flow<Car> {
        return repository.getCarById(id)
    }
}

class SearchCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(query: String): Flow<List<Car>> {
        return repository.searchCars(query)
    }
}

class FilterCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(
        minPrice: Double? = null,
        maxPrice: Double? = null,
        brand: String? = null,
        model: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        fuelType: String? = null,
        transmission: String? = null
    ): Flow<List<Car>> {
        return repository.filterCars(
            minPrice = minPrice,
            maxPrice = maxPrice,
            brand = brand,
            model = model,
            minYear = minYear,
            maxYear = maxYear,
            fuelType = fuelType,
            transmission = transmission
        )
    }
}
