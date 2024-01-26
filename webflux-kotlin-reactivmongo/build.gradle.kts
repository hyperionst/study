

dependencies {
	api(project(":core"))

	//mongodb
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	//redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

	//web-flux
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//tools
	testImplementation("io.projectreactor:reactor-test")
}

//
tasks.bootJar {
	enabled = true
}

tasks.jar {
	enabled = true
}
