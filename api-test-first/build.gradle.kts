
plugins{
    kotlin("plugin.jpa") version "1.9.0" // <-- 추가됨: mysql
}


dependencies {
    //로그 의존성
    api(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-validation")


    //mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("com.mysql:mysql-connector-j")  //mysql 8.x부터는 바꼈다고 한다.


}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
