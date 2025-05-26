package com.carapp.backend.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "favorites")
@IdClass(FavoriteId::class)
data class Favorite(
    @Id
    @ManyToOne @JoinColumn(name = "user_id")
    val user: User,

    @Id
    @ManyToOne @JoinColumn(name = "listing_id")
    val listing: CarListing
)
