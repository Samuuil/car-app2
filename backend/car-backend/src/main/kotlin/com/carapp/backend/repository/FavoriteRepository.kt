package com.carapp.backend.repository

import com.carapp.backend.model.entity.Favorite
import com.carapp.backend.model.entity.User
import com.carapp.backend.model.entity.CarListing
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : JpaRepository<Favorite, Int> {
    fun findByUser(user: User, pageable: Pageable): Page<Favorite>
    fun existsByUserAndListing(user: User, listing: CarListing): Boolean
    fun deleteByUserAndListing(user: User, listing: CarListing)
}
