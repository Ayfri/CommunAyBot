import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val kordexVersion: String by project
val logbackVersion: String by project
val kotlinLoggingVersion: String by project
val groovyVersion: String by project


plugins {
    kotlin("jvm") version "1.5.10"
    application
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
    implementation("com.kotlindiscord.kord.extensions:kord-extensions:$kordexVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.codehaus.groovy:groovy:$groovyVersion")
    implementation(kotlin("stdlib-jdk8"))
}



tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

application {
    mainClass.set("MainKT")
}
