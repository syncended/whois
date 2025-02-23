plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "dev.syncended.whois"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.commons.net)
    testImplementation(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.junit)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}