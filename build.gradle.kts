import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    checkstyle
    id("io.freefair.lombok") version "9.5.0"
    id("com.github.ben-manes.versions") version "0.54.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
  mainClass.set("org.example.hexlet.HelloWorld")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.4.240")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.3")
    implementation("org.apache.commons:commons-text:1.15.0")
    implementation("gg.jte:jte:3.2.4")
    implementation("org.slf4j:slf4j-simple:2.0.18")
    implementation("io.javalin:javalin:7.2.2")
    implementation("io.javalin:javalin-bundle:7.2.2")
    implementation("io.javalin:javalin-rendering:7.2.2")

    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}
