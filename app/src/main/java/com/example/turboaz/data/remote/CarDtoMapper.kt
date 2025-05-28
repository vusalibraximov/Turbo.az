package com.example.turboaz.data.remote

import com.example.turboaz.domain.model.Car
import com.example.turboaz.data.remote.dto.CarDto

// DTO'dan Domain model'e dönüşüm fonksiyonu
fun CarDto.toCar(): Car {
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

