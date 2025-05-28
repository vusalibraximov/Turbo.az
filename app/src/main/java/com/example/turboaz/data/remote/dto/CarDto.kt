package com.example.turboaz.data.remote.dto

data class CarDto(
    val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val price: Double,
    val mileage: Int,
    val fuelType: String,
    val transmission: String,
    val color: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val sellerName: String,
    val sellerPhone: String,
    val createdAt: String
)
