package com.hyperion.springsecuritymvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleAdminApplication

fun main(args: Array<String>) {
	runApplication<SimpleAdminApplication>(*args)

}
