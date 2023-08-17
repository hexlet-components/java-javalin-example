plugins {
    application
    id("checkstyle")
    id("io.freefair.lombok") version "8.1.0"
    id("com.github.ben-manes.versions") version "0.47.0"
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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("gg.jte:jte:3.0.2")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.javalin:javalin:5.6.2")
    implementation("io.javalin:javalin-rendering:5.6.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "org.example.hexlet.HelloWorld"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
