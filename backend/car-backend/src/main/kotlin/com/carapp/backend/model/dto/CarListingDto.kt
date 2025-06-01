package com.carapp.backend.model.dto

import java.math.BigDecimal

data class CreateCarListingRequest(
    val carSpecId: Long,
    val color: String?,
    val kilometers: Int?,
    val price: BigDecimal,
    val description: String?
)

data class CarListingResponse(
    val id: Int,
    val make: String,
    val model: String,
    val year: Int,
    val color: String?,
    val kilometers: Int?,
    val price: BigDecimal,
    val description: String?,
    val engineType: String?,
    val fuelType: String?,
    val cylinderCount: Int?,
    val engineSizeLiters: Double?,
    val horsepower: Int?,
    val transmissionType: String?,
    val driveType: String?,
    val sellerUsername: String,
    val createdAt: String
)

data class CarListingFilter(
    val make: String? = null,
    val model: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val minPrice: BigDecimal? = null,
    val maxPrice: BigDecimal? = null,
    val minKilometers: Int? = null,
    val maxKilometers: Int? = null,
    val engineType: String? = null,
    val fuelType: String? = null,
    val transmissionType: String? = null,
    val driveType: String? = null
)