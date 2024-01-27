plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springframework:spring-tx")
    implementation("com.h2database:h2:2.2.224")
}