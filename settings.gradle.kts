pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":aop:timer")
include(":core:conversion")
include(":core:databind")
include(":core:ioc-container")
include(":core:message-source")
include(":core:resource")
include(":data-access:tx")
include(":testing:junit-jupiter")
include(":testing:scoped")