buildscript {
    val kotlinVersion = "1.5.20"
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
    configurations.classpath.get().resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion(kotlinVersion)
        }
    }
}
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}
