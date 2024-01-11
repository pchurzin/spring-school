pluginManagement {
    val kotlinPluginVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinPluginVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

