plugins{
    kotlin("plugin.jpa") version "1.9.0" // <-- 추가됨: mysql : all-Open
}


dependencies {
    //로그 의존성
    api(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-validation")


    //mysql & jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("com.mysql:mysql-connector-j")  //mysql 8.x부터는 바꼈다고 한다.

    //linecorp-jdsl  :: https://github.com/line/kotlin-jdsl/tree/main/docs/ko/jpql-with-kotlin-jdsl
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.3.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.3.0")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.3.0")   //Kotlin JDSL은 DSL로 생성된 쿼리를 실행시킬 수 있는 Support dependency



    //


}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
