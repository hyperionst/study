dependencies {
    //기존 프로젝트 의존성 
    api(project(":core"))
    
    //하위 프로젝트에서 따로 사용할 종속성
    implementation("org.springframework.boot:spring-boot-starter-web")

    //Reactor Core
    implementation("io.projectreactor:reactor-core:3.1.1.RELEASE")
}


//
tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
