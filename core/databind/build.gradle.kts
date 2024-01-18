import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:${getKotlinPluginVersion()}")
}