import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:${getKotlinPluginVersion()}")
}