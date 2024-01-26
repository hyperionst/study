dependencies {

	//로그 의존성
	api(project(":core"))

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("io.asyncer:r2dbc-mysql")

}



tasks.bootJar {
	enabled = true
}

tasks.jar {
	enabled = true
}
