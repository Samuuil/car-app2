package com.carapp.backend.model.entity

import com.carapp.backend.model.enums.*
import jakarta.persistence.*

@Entity
@Table(name = "car_specs")
data class CarSpec(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val make: String,
    val model: String,
    val year: Int,

    @Enumerated(EnumType.STRING)
    val engineType: EngineType? = null,

    @Enumerated(EnumType.STRING)
    val fuelType: FuelType? = null,

    val cylinderCount: Int? = null,
    val engineSizeLiters: Double? = null,
    val horsepower: Int? = null,

    @Enumerated(EnumType.STRING)
    val transmissionType: TransmissionType? = null,

    @Enumerated(EnumType.STRING)
    val driveType: DriveType? = null
)
