package com.carapp.backend.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "car_specs")
data class CarSpec(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val make: String,
    val model: String,
    val year: Int,

    val engineType: String? = null,
    val fuelType: String? = null,
    val cylinderCount: Int? = null,
    val engineSizeLiters: Double? = null,
    val horsepower: Int? = null,
    val transmissionType: String? = null,
    val driveType: String? = null
)

