package com.carapp.backend.repository

import com.carapp.backend.model.entity.CarListing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CarListingRepository : JpaRepository<CarListing, Int>, JpaSpecificationExecutor<CarListing>