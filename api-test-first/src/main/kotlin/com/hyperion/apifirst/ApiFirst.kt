package com.hyperion.apifirst

import com.hyperion.core.logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ApiFirst


fun main(args: Array<String>) {
	runApplication<ApiFirst>(*args)

	logger.info { "api-first run" }
}

@RestController
class OnamaewaController(){
	@GetMapping("/")
	fun onamaewa () : String {
		return "8081 - first"
	}
}

