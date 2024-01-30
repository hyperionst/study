plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "01-study"
include("core")
include("api-test-first")
include("api-test-second")
include("webflux-kotlin-reactivmongo")
include("spring-data-jpa-criteria")
include("spring-data-jpa-criteria:arrow-example")
include("kotlin-rx-arrow-example")
include("r2bdc-webflux-example")
include("simple-admin")
include("spring-security-6-mvc")
include("spring-security-6-webflux")
