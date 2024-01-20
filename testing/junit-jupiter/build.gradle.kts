plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-context")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework:spring-test")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


tasks.withType<Test> {
    useJUnitPlatform()
}