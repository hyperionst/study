dependencies {

	//로그 의존성
	api(project(":core"))

	//async mysql
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("io.asyncer:r2dbc-mysql")

	//webflux 추가
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("io.projectreactor:reactor-test")
	
	//Kotlin Coroutine & webflux-extension 추가
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")



}



tasks.bootJar {
	enabled = true
}

tasks.jar {
	enabled = true
}
