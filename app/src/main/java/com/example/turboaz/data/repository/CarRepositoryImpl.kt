package com.example.turboaz.data.repository

import com.example.turboaz.data.remote.CarApiService
import com.example.turboaz.domain.model.Car
import com.example.turboaz.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepositoryImpl @Inject constructor(
    private val api: CarApiService
) : CarRepository {

    override suspend fun getCars(): Flow<List<Car>> = flow {
        try {
            val cars = api.getCars().map { it.toCar() }
            emit(cars)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getCarById(id: String): Flow<Car> = flow {
        try {
            val car = api.getCarById(id).toCar()
            emit(car)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun searchCars(query: String): Flow<List<Car>> = flow {
        try {
            val cars = api.searchCars(query).map { it.toCar() }
            emit(cars)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun filterCars(
        minPrice: Double?,
        maxPrice: Double?,
        brand: String?,
        model: String?,
        minYear: Int?,
        maxYear: Int?,
        fuelType: String?,
        transmission: String?
    ): Flow<List<Car>> = flow {
        try {
            val cars = api.filterCars(
                minPrice = minPrice,
                maxPrice = maxPrice,
                brand = brand,
                model = model,
                minYear = minYear,
                maxYear = maxYear,
                fuelType = fuelType,
                transmission = transmission
            ).map { it.toCar() }
            emit(cars)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }
}
