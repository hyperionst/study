package com.hyperion.r2bdcwebfluxexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories


/**
 * ## Mysql + r2bdc + webflux + kotlin
 *
 * [https://www.bezkoder.com/spring-r2dbc-mysql/](https://www.bezkoder.com/spring-r2dbc-mysql/)
 */
@EnableR2dbcRepositories
@SpringBootApplication
class R2bdcWebfluxExampleApplication

fun main(args: Array<String>) {
	runApplication<R2bdcWebfluxExampleApplication>(*args)
}
