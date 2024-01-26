
dependencies {
    //로그 의존성
    api(project(":core"))
    //TEST
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    //arrow 기본라이브러리
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")

    //rxJava : 책과는 다르게 Kotlin / java를 둘다 넣어야 된다... 뭐지...
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    //coroutin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    //Apach Async Http
    implementation("com.netflix.rxjava:rxjava-apache-http:0.20.7")

}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = false
}
