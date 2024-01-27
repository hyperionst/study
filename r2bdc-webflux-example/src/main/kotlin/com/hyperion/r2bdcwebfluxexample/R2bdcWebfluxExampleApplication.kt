package com.hyperion.r2bdcwebfluxexample


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux

//import org.springframework.context.annotation.Bean
//import org.springframework.core.io.ClassPathResource
//import io.r2dbc.spi.ConnectionFactory
//import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
//import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator



/**
 * ## Mysql + r2bdc + webflux + kotlin
 *
 * [https://www.bezkoder.com/spring-r2dbc-mysql/](https://www.bezkoder.com/spring-r2dbc-mysql/)
 *
 * > tutorial 에서는 ConnectionFactoryInitializer 를 사용하지만 // 지금 버전에서는 timezone에러가 발생한다..
 *  그부분을 제거하고 진행하면 정상적으로 진행된다.
 *
 */
@EnableR2dbcRepositories
@SpringBootApplication
@EnableWebFlux
class R2bdcWebfluxExampleApplication

//@Bean
//fun initializer(connectionFactory: ConnectionFactory?): ConnectionFactoryInitializer {
//	val initializer = ConnectionFactoryInitializer()
//	initializer.setConnectionFactory(connectionFactory!!)
//	initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
//
//	return initializer
//}


fun main(args: Array<String>) {
	runApplication<R2bdcWebfluxExampleApplication>(*args)
}

