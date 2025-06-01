package com.carapp.backend.model.entity

import com.carapp.backend.model.enums.*
import jakarta.persistence.*

@Entity
@Table(name = "car_specs")
data class CarSpec(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val make: String,
    val model: String,
    val year: Int,

    val engineType: String? = null,          // was EngineType
    val fuelType: String? = null,            // was FuelType
    val cylinderCount: Int? = null,
    val engineSizeLiters: Double? = null,
    val horsepower: Int? = null,
    val transmissionType: String? = null,    // was TransmissionType
    val driveType: String? = null            // was DriveType
)

