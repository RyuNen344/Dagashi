plugins {
    id("jacoco")
    id("com.releaseshub.gradle.plugin").version(Dep.GradlePlugin.releaseHubVersion)
    id("org.jlleitschuh.gradle.ktlint").version(Dep.GradlePlugin.ktlintVersion)
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
    apply(plugin = "jacoco")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/lisawray/maven")
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.suppressWarnings = false
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
            "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
        )
    }
    afterEvaluate {
        jacoco {
            toolVersion = "0.8.2"
        }
        ktlint {
            verbose.set(true)
            android.set(true)
            ignoreFailures.set(true)
            coloredOutput.set(true)
            outputColorName.set("RED")
            additionalEditorconfigFile.set(file("${rootDir.absolutePath}/.editorconfig"))
            reporters {
                reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            }
            filter {
                exclude("**/generated/**")
                include("**/kotlin/**")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    group = "cleanup"
    delete(rootProject.buildDir)
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
    executionData.setFrom(
        (tasks.getByName("jacocoMerge") as JacocoMerge).destinationFile.absolutePath
    )

    gradle.afterProject {
        if (rootProject != this && plugins.hasPlugin("jacoco")) {
            sourceDirectories.from += "$projectDir/src/main/java"
            val current = classDirectories.files.toMutableSet()
            current.addAll(fileTree("$buildDir/tmp/kotlin-classes/debug").files)
            current.addAll(fileTree("$buildDir/intermediates/javac/debug/classes").files)
            classDirectories.setFrom(current.map { it.absolutePath })
        }
    }
}

releasesHub {
    dependenciesBasePath = "buildSrc/src/main/kotlin/"
    dependenciesClassNames = listOf("Dep.kt")
    pullRequestEnabled = true
    gitHubRepositoryOwner = "RyuNen344"
    gitHubRepositoryName = "Dagashi"
    pullRequestsMax = 20
    gitHubUserName = "RyuNen344"
    gitHubUserEmail = "s1100633@outlook.com"
}
