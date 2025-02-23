plugins {
    kotlin("jvm") version "2.1.0"
}

group = "dev.syncended.whois"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}