plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")
}