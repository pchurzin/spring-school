pluginManagement {
    val kotlinPluginVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinPluginVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinPluginVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":core:conversion")
include(":core:databind")
include(":core:ioc-container")
include(":core:message-source")
include(":core:resource")
