import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    alias(libs.plugins.spotless)
    alias(libs.plugins.lombok)
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.shadow)
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
    implementation(libs.h2)
    implementation(libs.hikariCp)
    implementation(libs.jacksonDatabind)
    implementation(libs.commonsText)
    implementation(libs.jte)
    implementation(libs.slf4jSimple)
    implementation(libs.javalin)
    implementation(libs.javalinBundle)
    implementation(libs.javalinRenderingJte)

    testImplementation(libs.assertjCore)
    testImplementation(platform(libs.junitBom))
    testImplementation(libs.junitJupiter)
    testRuntimeOnly(libs.junitPlatformLauncher)
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

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat().aosp()
        formatAnnotations()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
}

// versionCatalogUpdate пишет свежие версии прямо в gradle/libs.versions.toml,
// поэтому руками их сверять не нужно. Ключи не сортируются: порядок в каталоге
// смысловой, по группам зависимостей.
versionCatalogUpdate {
    sortByKey = false
}
