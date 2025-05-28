package com.example.turboaz.data.remote.dto

import com.example.turboaz.domain.model.Car
import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("mileage")
    val mileage: Int,
    @SerializedName("fuel_type")
    val fuelType: String,
    @SerializedName("transmission")
    val transmission: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("seller_name")
    val sellerName: String,
    @SerializedName("seller_phone")
    val sellerPhone: String,
    @SerializedName("created_at")
    val createdAt: String
) {
    fun toCar(): Car {
        return Car(
            id = id,
            brand = brand,
            model = model,
            year = year,
            price = price,
            mileage = mileage,
            fuelType = fuelType,
            transmission = transmission,
            color = color,
            description = description,
            imageUrl = imageUrl,
            location = location,
            sellerName = sellerName,
            sellerPhone = sellerPhone,
            createdAt = createdAt
        )
    }
}
