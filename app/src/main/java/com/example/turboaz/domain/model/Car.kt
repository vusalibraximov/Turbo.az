package com.example.turboaz.domain.model

data class Car(
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

enum class FuelType {
    GASOLINE,
    DIESEL,
    ELECTRIC,
    HYBRID
}

enum class Transmission {
    MANUAL,
    AUTOMATIC,
    SEMI_AUTOMATIC
}
