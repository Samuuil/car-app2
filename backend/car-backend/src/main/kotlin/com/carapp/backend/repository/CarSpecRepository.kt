package com.carapp.backend.repository

import com.carapp.backend.model.entity.CarSpec
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarSpecRepository : JpaRepository<CarSpec, Long> {

}
