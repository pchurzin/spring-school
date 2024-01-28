import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    id("ru.pchurzin.spring.school.kotlin")
}

dependencies {
    implementation(platform(libs.spring.framework.bom))
    implementation("org.springframework:spring-context")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${getKotlinPluginVersion()}")
    implementation("org.aspectj:aspectjweaver:1.9.21")
}