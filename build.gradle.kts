import org.jreleaser.model.Active

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jreleaser)
    `maven-publish`
    `java-library`
}

group = "dev.syncended.whois"
version = releaseVersion()

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

java {
    withJavadocJar()
    withSourcesJar()
}


tasks["publish"].doFirst {
    File(layout.buildDirectory.get().asFile, "jreleaser").mkdirs()
}

publishing {
    publications {
        repositories {
            maven { url = stagingDir().toURI() }
        }
        create<MavenPublication>("mavenPublications") {
            from(components["java"])
            groupId = "dev.syncended"
            artifactId = "whois"
            version = releaseVersion()

            pom {
                name = "whois"
                description = "Whois Library"
                url = "https://github.com/syncended/whois"
                scm {
                    connection = "scm:git:https://github.com/syncended/whois"
                    developerConnection = "scm:git:https://github.com/syncended/"
                    url = "https://github.com/syncended/whois"
                }

                licenses {
                    license {
                        name = "Apache-2.0 license"
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                    }
                }
                developers {
                    developer {
                        id = "syncended"
                        name = "Mikhail Ivanov"
                        email = "syncended@gmail.com"
                    }
                }
            }
        }
    }
}

jreleaser {
    dryrun = false
    gitRootSearch = true
    version = releaseVersion()

    signing {
        active = Active.ALWAYS
        armored = true
        verify = true
        artifacts = true
        files = true

        publicKey = gpgPublicKey()
        secretKey = gpgKey()
        passphrase = gpgPassword()
    }
    deploy {
        maven {
            mavenCentral {
                active = Active.ALWAYS
                create("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    println("Sign dir: ${stagingDir()}")
                    stagingRepository(stagingDir().toString())

                    username = mavenUsername()
                    password = mavenPassword()
                }
            }
        }
    }
    release {
        github {
            skipRelease = true
            skipTag = true
            overwrite = false
            token = "none"
        }
    }
}


private fun stagingDir(): File {
    return layout.buildDirectory.dir("staging").get().asFile
}

private fun releaseVersion(): String {
    return System.getenv("RELEASE_VERSION")
        ?: "${System.currentTimeMillis()}-SNAPSHOT"
}

private fun mavenUsername(): String {
    return System.getenv("SONATYPE_USERNAME") ?: ""
}

private fun mavenPassword(): String {
    return System.getenv("SONATYPE_PASSWORD") ?: ""
}

private fun gpgPublicKey(): String? {
    return System.getenv("GPG_PUBLIC_KEY")
}

private fun gpgKey(): String? {
    return System.getenv("GPG_KEY")
}

private fun gpgPassword(): String? {
    return System.getenv("GPG_PASSWORD")
}
