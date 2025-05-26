package com.carapp.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarBackendApplication

fun main(args: Array<String>) {
	runApplication<CarBackendApplication>(*args)
}
