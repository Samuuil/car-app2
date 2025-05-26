package com.carapp.backend.model.entity

import java.io.Serializable

data class FavoriteId(
    val user: Long = 0,
    val listing: Long = 0
) : Serializable
