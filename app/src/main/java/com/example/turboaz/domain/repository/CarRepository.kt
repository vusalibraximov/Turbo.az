package com.example.turboaz.domain.repository

import com.example.turboaz.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    suspend fun getCars(): Flow<List<Car>>
    suspend fun getCarById(id: String): Flow<Car>
    suspend fun searchCars(query: String): Flow<List<Car>>
    suspend fun filterCars(
        minPrice: Double? = null,
        maxPrice: Double? = null,
        brand: String? = null,
        model: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        fuelType: String? = null,
        transmission: String? = null
    ): Flow<List<Car>>
}
