plugins {
    `kotlin-dsl`
}

val kotlinVersion:String by project
dependencies {
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
}