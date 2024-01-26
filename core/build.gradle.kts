dependencies {
    //로그 의존성
    api("io.github.microutils:kotlin-logging-jvm:2.0.11")
}



tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
