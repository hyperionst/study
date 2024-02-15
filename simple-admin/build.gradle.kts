plugins{
	kotlin("plugin.jpa") version "1.9.0" // <-- 추가됨: mysql
}

dependencies {
	api(project(":core"))

	//Kotlin & Spring web basic
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	//mysql & jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	runtimeOnly("com.mysql:mysql-connector-j")  //mysql 8.x부터는 바꼈다고 한다.

	//Secret Manager 데이터 받아오기 추가
	implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:2.4.4")


	//Spring security
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")


	//linecorp-jdsl  :: https://github.com/line/kotlin-jdsl/tree/main/docs/ko/jpql-with-kotlin-jdsl
	implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.3.0")
	implementation("com.linecorp.kotlin-jdsl:jpql-render:3.3.0")
	//Kotlin JDSL은 DSL로 생성된 쿼리를 실행시킬 수 있는 Support dependency
	implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.3.0")

	//arrow 기본라이브러리
	implementation("io.arrow-kt:arrow-core:1.2.0")
	implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")

	//Kotlin Coroutine & webflux-extension 추가
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//Kafka
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.springframework.kafka:spring-kafka-test")

	//cache
	implementation("org.springframework.boot:spring-boot-starter-cache")
	//redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")


}

tasks.bootJar {
enabled = true
}

tasks.jar {
enabled = true
}

