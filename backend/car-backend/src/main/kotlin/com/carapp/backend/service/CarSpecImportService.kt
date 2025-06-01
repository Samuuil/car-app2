package com.carapp.backend.service

import com.carapp.backend.model.dto.CarApiTrimDto
import com.carapp.backend.model.entity.CarSpec
import com.carapp.backend.repository.CarSpecRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CarSpecImportService(
    private val carSpecRepository: CarSpecRepository
) {
    private val logger = LoggerFactory.getLogger(CarSpecImportService::class.java)

    @Value("\${carapi.token}")
    lateinit var apiToken: String

    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val restTemplate = RestTemplate()

    fun importSpecs(trimIds: List<Int>) {
        logger.info("Starting import of ${trimIds.size} car specs")
        
        for (id in trimIds) {
            val url = "https://carapi.app/api/trims/v2/$id"
            logger.info("Fetching data for trim ID: $id")

            val headers = HttpHeaders().apply {
                set("Authorization", "Bearer $apiToken")
            }
            val request = HttpEntity<String>(headers)

            try {
                val response: ResponseEntity<String> =
                    restTemplate.exchange(url, HttpMethod.GET, request, String::class.java)

                response.body?.let {
                    logger.debug("Received response for trim ID $id: $it")
                    val dto = objectMapper.readValue<CarApiTrimDto>(it)

                    val engine = dto.engines?.firstOrNull()
                    val transmission = dto.transmissions?.firstOrNull()
                    val driveType = dto.drive_types?.firstOrNull()

                    if (engine != null && transmission != null && driveType != null) {
                        val spec = CarSpec(
                            make = dto.make,
                            model = dto.model,
                            year = dto.year,
                            engineType = engine.engine_type,
                            fuelType = engine.fuel_type,
                            cylinderCount = engine.cylinders
                                ?.filter { it.isDigit() }
                                ?.toIntOrNull(),
                            engineSizeLiters = engine.size?.toDoubleOrNull(),
                            horsepower = engine.horsepower_hp,
                            transmissionType = transmission.description,
                            driveType = driveType.description
                        )

                        logger.info("Saving car spec for ${spec.make} ${spec.model} ${spec.year}")
                        val savedSpec = carSpecRepository.save(spec)
                        logger.info("Successfully saved car spec with ID: ${savedSpec.id}")
                    } else {
                        logger.warn("Missing required data for trim ID $id: engine=${engine != null}, transmission=${transmission != null}, driveType=${driveType != null}")
                    }
                } ?: run {
                    logger.warn("Empty response body received for trim ID $id")
                }
            } catch (ex: Exception) {
                logger.error("Failed to import trim ID $id: ${ex.message}", ex)
            }
        }
        logger.info("Finished importing car specs")
    }
}
