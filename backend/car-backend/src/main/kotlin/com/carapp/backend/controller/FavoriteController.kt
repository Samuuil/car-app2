package com.carapp.backend.controller

import com.carapp.backend.model.dto.CarListingResponse
import com.carapp.backend.service.FavoriteService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/favorites")
class FavoriteController(
    private val favoriteService: FavoriteService
) {
    @PostMapping("/{listingId}")
    fun addToFavorites(
        @PathVariable listingId: Int,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Unit> {
        val userId = userDetails.username.toInt()
        favoriteService.addToFavorites(userId, listingId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{listingId}")
    fun removeFromFavorites(
        @PathVariable listingId: Int,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Unit> {
        val userId = userDetails.username.toInt()
        favoriteService.removeFromFavorites(userId, listingId)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getFavorites(
        @AuthenticationPrincipal userDetails: UserDetails,
        pageable: Pageable
    ): ResponseEntity<Page<CarListingResponse>> {
        val userId = userDetails.username.toInt()
        return ResponseEntity.ok(favoriteService.getFavorites(userId, pageable))
    }

    @GetMapping("/{listingId}/check")
    fun isFavorite(
        @PathVariable listingId: Int,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Boolean> {
        val userId = userDetails.username.toInt()
        return ResponseEntity.ok(favoriteService.isFavorite(userId, listingId))
    }
}