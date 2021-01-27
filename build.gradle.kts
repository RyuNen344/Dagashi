plugins {
    id("jacoco")
}

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Dep.GradlePlugin.android)
        classpath(Dep.GradlePlugin.safeArgs)
        classpath(Dep.GradlePlugin.daggerHilt)
        classpath(Dep.GradlePlugin.kotlin)
        classpath(Dep.GradlePlugin.kotlinSerialization)
        classpath(Dep.GradlePlugin.releaseHub)
        classpath(Dep.GradlePlugin.ktlint)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/lisawray/maven")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

jacoco {
    toolVersion = "0.8.5"
}

task("jacocoMerge", JacocoMerge::class) {
    group = "verification"
    gradle.afterProject {
        if (rootProject != this && plugins.hasPlugin("jacoco")) {
            executionData(
                fileTree(buildDir) {
                    includes += mutableSetOf("**/*.exec", "**/*.ec")
                }
            )
        }
    }
}

task("jacocoMergedReport", JacocoReport::class) {
    group = "verification"
    dependsOn(tasks.getByName("jacocoMerge"))
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
    }
    executionData.setFrom((tasks.getByName("jacocoMerge") as JacocoMerge).destinationFile.absolutePath)

    gradle.afterProject {
        if (rootProject != this && plugins.hasPlugin("jacoco")) {
            sourceDirectories.from += "$projectDir/src/main/java"
            val current = classDirectories.files.toMutableSet()
            current.addAll(fileTree("$buildDir/tmp/kotlin-classes/debug"))
            current.addAll(fileTree("$buildDir/intermediates/javac/debug/classes"))
            classDirectories.setFrom(current.map { it.absolutePath })
        }
    }
}
