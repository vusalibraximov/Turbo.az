package com.example.turboaz.data.repository

import com.example.turboaz.data.remote.CarApiService
import com.example.turboaz.data.remote.toCar
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
            val carsDto = api.getCars()
            val cars = carsDto.map { it.toCar() }
            emit(cars)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun getCarById(id: String): Flow<Car> = flow {
        try {
            val carDto = api.getCarById(id)
            emit(carDto.toCar())
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun searchCars(query: String): Flow<List<Car>> = flow {
        try {
            val carsDto = api.searchCars(query)
            val cars = carsDto.map { it.toCar() }
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
            val carsDto = api.filterCars(
                minPrice,
                maxPrice,
                brand,
                model,
                minYear,
                maxYear,
                fuelType,
                transmission
            )
            val cars = carsDto.map { it.toCar() }
            emit(cars)
        } catch (e: HttpException) {
            throw Exception("An unexpected error occurred")
        } catch (e: IOException) {
            throw Exception("Couldn't reach server. Check your internet connection")
        }
    }
}
