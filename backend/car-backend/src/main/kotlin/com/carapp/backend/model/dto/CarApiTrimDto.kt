package com.carapp.backend.model.dto

data class CarApiTrimDto(
    val id: Int,
    val make: String,
    val model: String,
    val year: Int,
    val engines: List<EngineDto>?,
    val transmissions: List<TransmissionDto>?,
    val drive_types: List<DriveTypeDto>?
)

data class EngineDto(
    val engine_type: String?,
    val fuel_type: String?,
    val cylinders: String?,
    val size: String?,
    val horsepower_hp: Int?
)

data class TransmissionDto(
    val description: String?
)

data class DriveTypeDto(
    val description: String?
)
