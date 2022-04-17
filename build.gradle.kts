import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "fr.ayfri"
version = "0.1"

repositories {
    maven {
        name = "Kotlin Discord"
        setUrl("https://maven.kotlindiscord.com/repository/maven-public/")
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(10, "minutes")
    resolutionStrategy.cacheDynamicVersionsFor(10, "minutes")
}

dependencies {
    implementation(libs.kord.extensions)
    implementation(libs.kotlin.stdlib)
    implementation(libs.logback)
    implementation(libs.kotlinLogging)
    implementation(libs.groovy)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

application {
    mainClass.set("MainKT")
}
