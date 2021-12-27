import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.releaseHub)
    alias(libs.plugins.detekt)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.androidx.navigation.gradle)
        classpath(libs.dagger.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.kotlin.serialization.gradle)
    }
}

allprojects {
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
}

tasks.register("clean", Delete::class) {
    group = "cleanup"
    delete(rootProject.buildDir)
}

releasesHub {
    pullRequestEnabled = true
    gitHubRepositoryOwner = "RyuNen344"
    gitHubRepositoryName = "Dagashi"
    pullRequestsMax = 20
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    ignoreFailures = true
    config = files("$projectDir/.detekt/config.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("$projectDir/.detekt/baseline.xml") // a way of suppressing issues before introducing detekt
    source = files(rootDir)
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
    }
}
