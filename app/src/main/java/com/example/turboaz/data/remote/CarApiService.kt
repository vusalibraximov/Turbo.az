package com.example.turboaz.data.remote

import com.example.turboaz.data.remote.dto.CarDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarApiService {

    companion object {
        const val BASE_URL = "https://api.turbo.az/v1/"
    }

    @GET("cars")
    suspend fun getCars(): List<CarDto>

    @GET("cars/{id}")
    suspend fun getCarById(@Path("id") id: String): CarDto

    @GET("cars/search")
    suspend fun searchCars(@Query("query") query: String): List<CarDto>

    @GET("cars/filter")
    suspend fun filterCars(
        @Query("minPrice") minPrice: Double?,
        @Query("maxPrice") maxPrice: Double?,
        @Query("brand") brand: String?,
        @Query("model") model: String?,
        @Query("minYear") minYear: Int?,
        @Query("maxYear") maxYear: Int?,
        @Query("fuelType") fuelType: String?,
        @Query("transmission") transmission: String?
    ): List<CarDto>
}
