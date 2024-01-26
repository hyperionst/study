import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

	id("org.springframework.boot") version "3.1.7" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
	kotlin("plugin.spring") version "1.9.0" apply false
	//jvm은 그대로 둬야함
	kotlin("jvm") version "1.9.0"

	//javadoc관련 다운로드 설정
	id("idea")
}


//javadoc관련 다운로드 설정
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

//전체 프로젝트에서 사용될 공통속성지정
allprojects {
	group = "com.hyperion"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}


	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}
}


//하위 프로젝트에 연계 해주고 싶은 속성들
subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "idea")

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		runtimeOnly("org.springframework.boot:spring-boot-devtools")
		testImplementation(kotlin("test"))
	}

	java.sourceCompatibility = JavaVersion.VERSION_17
	java.targetCompatibility = JavaVersion.VERSION_17

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}


