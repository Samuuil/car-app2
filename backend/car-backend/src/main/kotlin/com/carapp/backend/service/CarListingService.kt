package com.carapp.backend.service

import com.carapp.backend.model.dto.CarListingFilter
import com.carapp.backend.model.dto.CarListingResponse
import com.carapp.backend.model.dto.CreateCarListingRequest
import com.carapp.backend.model.entity.CarListing
import com.carapp.backend.model.entity.CarSpec
import com.carapp.backend.model.entity.User
import com.carapp.backend.repository.CarListingRepository
import com.carapp.backend.repository.CarSpecRepository
import com.carapp.backend.repository.UserRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class CarListingService(
    private val carListingRepository: CarListingRepository,
    private val carSpecRepository: CarSpecRepository,
    private val userRepository: UserRepository
) {
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    @Transactional
    fun createListing(request: CreateCarListingRequest, userId: Int): CarListingResponse {
        val user = userRepository.findById(userId.toInt()).orElseThrow { NoSuchElementException("User not found") }
        val carSpec = carSpecRepository.findById(request.carSpecId).orElseThrow { NoSuchElementException("Car spec not found") }

        val listing = CarListing(
            user = user,
            carSpec = carSpec,
            color = request.color,
            kilometers = request.kilometers,
            price = request.price,
            description = request.description
        )

        val savedListing = carListingRepository.save(listing)
        return mapToResponse(savedListing)
    }

    fun getListings(filter: CarListingFilter, pageable: Pageable): Page<CarListingResponse> {
        val specification = createSpecification(filter)
        return carListingRepository.findAll(specification, pageable).map { mapToResponse(it) }
    }

    private fun createSpecification(filter: CarListingFilter): Specification<CarListing> {
        return Specification { root, _, cb ->
            val predicates = mutableListOf<Predicate>()

            filter.make?.let {
                predicates.add(cb.like(cb.lower(root.get<CarSpec>("carSpec").get<String>("make")), "%${it.lowercase()}%"))
            }
            filter.model?.let {
                predicates.add(cb.like(cb.lower(root.get<CarSpec>("carSpec").get<String>("model")), "%${it.lowercase()}%"))
            }
            filter.minYear?.let {
                predicates.add(cb.greaterThanOrEqualTo(root.get<CarSpec>("carSpec").get<Int>("year"), it))
            }
            filter.maxYear?.let {
                predicates.add(cb.lessThanOrEqualTo(root.get<CarSpec>("carSpec").get<Int>("year"), it))
            }
            filter.minPrice?.let {
                predicates.add(cb.greaterThanOrEqualTo(root.get<Double>("price"), it.toDouble()))
            }
            filter.maxPrice?.let {
                predicates.add(cb.lessThanOrEqualTo(root.get<Double>("price"), it.toDouble()))
            }
            filter.minKilometers?.let {
                predicates.add(cb.greaterThanOrEqualTo(root.get<Int>("kilometers"), it))
            }
            filter.maxKilometers?.let {
                predicates.add(cb.lessThanOrEqualTo(root.get<Int>("kilometers"), it))
            }
            filter.engineType?.let {
                predicates.add(cb.equal(root.get<CarSpec>("carSpec").get<String>("engineType"), it))
            }
            filter.fuelType?.let {
                predicates.add(cb.equal(root.get<CarSpec>("carSpec").get<String>("fuelType"), it))
            }
            filter.transmissionType?.let {
                predicates.add(cb.equal(root.get<CarSpec>("carSpec").get<String>("transmissionType"), it))
            }
            filter.driveType?.let {
                predicates.add(cb.equal(root.get<CarSpec>("carSpec").get<String>("driveType"), it))
            }

            cb.and(*predicates.toTypedArray())
        }
    }

    fun mapToResponse(listing: CarListing): CarListingResponse {
        val carSpec = listing.carSpec!!
        return CarListingResponse(
            id = listing.id,
            make = carSpec.make,
            model = carSpec.model,
            year = carSpec.year,
            color = listing.color,
            kilometers = listing.kilometers,
            price = listing.price,
            description = listing.description,
            engineType = carSpec.engineType,
            fuelType = carSpec.fuelType,
            cylinderCount = carSpec.cylinderCount,
            engineSizeLiters = carSpec.engineSizeLiters,
            horsepower = carSpec.horsepower,
            transmissionType = carSpec.transmissionType,
            driveType = carSpec.driveType,
            sellerUsername = listing.user.username,
            createdAt = listing.createdAt.format(dateFormatter)
        )
    }
}