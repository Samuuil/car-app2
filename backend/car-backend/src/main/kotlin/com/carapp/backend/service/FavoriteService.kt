package com.carapp.backend.service

import com.carapp.backend.model.dto.CarListingResponse
import com.carapp.backend.model.entity.CarListing
import com.carapp.backend.model.entity.Favorite
import com.carapp.backend.model.entity.User
import com.carapp.backend.repository.CarListingRepository
import com.carapp.backend.repository.FavoriteRepository
import com.carapp.backend.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository,
    private val carListingRepository: CarListingRepository,
    private val carListingService: CarListingService
) {
    @Transactional
    fun addToFavorites(userId: Int, listingId: Int) {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found") }
        val listing = carListingRepository.findById(listingId).orElseThrow { NoSuchElementException("Listing not found") }

        if (favoriteRepository.existsByUserAndListing(user, listing)) {
            throw IllegalArgumentException("Listing is already in favorites")
        }

        val favorite = Favorite(user = user, listing = listing)
        favoriteRepository.save(favorite)
    }

    @Transactional
    fun removeFromFavorites(userId: Int, listingId: Int) {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found") }
        val listing = carListingRepository.findById(listingId).orElseThrow { NoSuchElementException("Listing not found") }

        favoriteRepository.deleteByUserAndListing(user, listing)
    }

    fun getFavorites(userId: Int, pageable: Pageable): Page<CarListingResponse> {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found") }
        return favoriteRepository.findByUser(user, pageable)
            .map { carListingService.mapToResponse(it.listing) }
    }

    fun isFavorite(userId: Int, listingId: Int): Boolean {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found") }
        val listing = carListingRepository.findById(listingId).orElseThrow { NoSuchElementException("Listing not found") }
        return favoriteRepository.existsByUserAndListing(user, listing)
    }
}