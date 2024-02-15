package com.hyperion.rediscacheexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class RedisCacheExampleApplication

fun main(args: Array<String>) {
	runApplication<RedisCacheExampleApplication>(*args)
}
