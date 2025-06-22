plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.batch.bom))
    implementation("org.springframework.batch:spring-batch-core")
    implementation("org.springframework:spring-jdbc")
    implementation("com.h2database:h2:2.2.224")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.17")
}