package com.carapp.backend.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val passwordHash: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
