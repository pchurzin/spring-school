plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.18")
    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework:spring-test")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


tasks.withType<Test> {
    useJUnitPlatform()
}