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
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}