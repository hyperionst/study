plugins{
	kotlin("plugin.jpa") version "1.9.0" // <-- 추가됨: mysql
}

dependencies {
	api(project(":core"))

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	//Spring security
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")


	//Kotlin Coroutine & webflux-extension 추가
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")


	implementation("org.springframework.boot:spring-boot-starter-web")

}

tasks.bootJar {
enabled = true
}

tasks.jar {
enabled = true
}

