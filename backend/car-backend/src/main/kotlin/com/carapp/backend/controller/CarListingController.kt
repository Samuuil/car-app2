package com.carapp.backend.controller

import com.carapp.backend.model.dto.CarListingFilter
import com.carapp.backend.model.dto.CarListingResponse
import com.carapp.backend.model.dto.CreateCarListingRequest
import com.carapp.backend.service.CarListingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/listings")
class CarListingController(
    private val carListingService: CarListingService
) {
    @PostMapping
    fun createListing(
        @RequestBody request: CreateCarListingRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<CarListingResponse> {
        val userId = userDetails.username.toInt()
        return ResponseEntity.ok(carListingService.createListing(request, userId))
    }

    @GetMapping
    fun getListings(
        filter: CarListingFilter,
        pageable: Pageable
    ): ResponseEntity<Page<CarListingResponse>> {
        return ResponseEntity.ok(carListingService.getListings(filter, pageable))
    }
}