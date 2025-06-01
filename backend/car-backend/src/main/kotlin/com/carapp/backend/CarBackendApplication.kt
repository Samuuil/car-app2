package com.carapp.backend

import com.carapp.backend.service.CarSpecImportService

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import jakarta.annotation.PostConstruct

@SpringBootApplication
class CarBackendApplication {

	@PostConstruct
	fun init() {
		println("🚀 === Car Backend Application Starting === 🚀")
		println("Car Backend Application is running...ASISAUDHIUADSIUASHDIAFIAHSDBIAFIUASIBFHADDNBHFAFIUHSDIAHFIUA")
	}
}

fun main(args: Array<String>) {
	println("🔥 === MAIN METHOD - Starting Spring Boot Application === 🔥")

	val context = runApplication<CarBackendApplication>(*args)

	println("🏁 === Spring Boot Application Context Fully Started === 🏁")

	// Run the car spec import after application startup
	try {
		println("🔥 === CAR SPEC IMPORT STARTING === 🔥")

		val carSpecImportService = context.getBean(CarSpecImportService::class.java)

		val trimIds = listOf(18336, 18337, 18334, 18335, 18304, 18302, 18303, 18305, 18306, 18345,
			18344, 18299, 18300, 18301, 18288, 18289, 18291, 18292, 18287, 18290,
			18311, 18312, 18314, 18313, 18351, 18352, 18347, 18348, 18270, 18282,
			18283, 18264, 18276, 18265, 18267, 18277, 18279, 18272, 18273, 18274,
			18275, 18271, 18266, 18278, 18268, 18269, 18280, 18281, 18293, 18294,
			18297, 18295, 18296, 18298, 18340, 18341, 18343, 18338, 18339, 18342,
			18329, 18324, 18321, 18322, 18323, 18325, 18326, 18327, 18328, 18353,
			18308, 18307, 18309, 18310, 18346, 18315, 18316, 18350, 18349, 18330,
			18331, 18319, 18317, 18333, 18332, 18320, 18318, 18286, 18284, 18285)

		println("About to import ${trimIds.size} car specs")

		carSpecImportService.importSpecs(trimIds)

		println("🎉 === CAR SPEC IMPORT COMPLETED === 🎉")

	} catch (e: Exception) {
		println("💥 === ERROR IN CAR SPEC IMPORT: ${e.message} === 💥")
		e.printStackTrace()
	}
}