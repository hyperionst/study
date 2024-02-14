

dependencies {
	api(project(":core"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.bootJar {
	enabled = true
}

tasks.jar {
	enabled = true
}