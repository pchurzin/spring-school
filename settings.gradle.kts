pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":aop:timer")
include(":boot:app")
include(":boot:observability")
include(":boot:weberror")
include(":core:conversion")
include(":core:databind")
include(":core:ioc-container")
include(":core:message-source")
include(":core:resource")
include(":data-access:tx")
include(":testing:junit-jupiter")
include(":testing:scoped")
include(":webmvc:config")
include(":webmvc:dispatcher-servlet:annotation")
include(":webmvc:dispatcher-servlet:tomcat")
include(":webmvc:dispatcher-servlet:xml")
