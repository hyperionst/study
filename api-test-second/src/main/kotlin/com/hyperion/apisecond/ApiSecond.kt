package com.hyperion.apisecond

import com.hyperion.core.logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class ApiSecond


fun main(args: Array<String>) {
	logger.info { "api-second run" }

	runApplication<ApiSecond>(*args)
}


@RestController
class OnamaewaController(){
	@GetMapping("/")
	fun onamaewa () : String {
		return "8082 - second"
	}
}
