plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.hits"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.9.1")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:4.9.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}