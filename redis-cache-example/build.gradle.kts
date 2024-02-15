
dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")

	//mysql & jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	runtimeOnly("com.mysql:mysql-connector-j")  //mysql 8.x부터는 바꼈다고 한다.


	//cache
	implementation("org.springframework.boot:spring-boot-starter-cache")
	//redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")



}

