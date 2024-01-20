plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")

    runtimeOnly(platform(libs.jakarta.ee.bom))
    runtimeOnly("jakarta.annotation:jakarta.annotation-api")
    runtimeOnly("jakarta.inject:jakarta.inject-api")
    runtimeOnly("jakarta.persistence:jakarta.persistence-api")
    runtimeOnly("org.springframework:spring-orm")
}