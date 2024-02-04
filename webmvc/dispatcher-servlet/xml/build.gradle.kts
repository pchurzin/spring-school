plugins {
    id("ru.pchurzin.spring.school.kotlin")
    war
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-webmvc")
}