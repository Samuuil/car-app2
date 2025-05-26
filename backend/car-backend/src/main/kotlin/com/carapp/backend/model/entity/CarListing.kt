package com.carapp.backend.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "car_listings")
data class CarListing(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne @JoinColumn(name = "car_spec_id")
    val carSpec: CarSpec? = null,

    val color: String? = null,
    val kilometers: Int? = null,

    @Column(nullable = false)
    val price: BigDecimal,

    val description: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
