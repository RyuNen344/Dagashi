import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("jacoco")
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
    apply(plugin = "jacoco")
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

jacoco {
    toolVersion = "0.8.7"
}

task("jacocoMergedReport", JacocoReport::class) {
    group = "verification"
    description = "create jacoco merged repot"
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    gradle.afterProject {

        val testVariant = "debug"

        // Exclude the class files corresponding to the auto-generated source files.
        val classDirectoriesTreeExcludes = setOf(
            // e.g. class   : androidx/databinding/library/baseAdapters/BR.class
            //      element : BR
            "androidx/**/*.class",

            // e.g. class   : <AndroidManifestPackage>/DataBinderMapperImpl.class
            //      element : DataBinderMapperImpl
            "**/DataBinderMapperImpl.class",

            // e.g. class   : <AndroidManifestPackage>/DataBinderMapperImpl$InnerBrLookup.class
            //      element : DataBinderMapperImpl.InnerBrLookup
            "**/DataBinderMapperImpl\$*.class",

            // e.g. class   : <AndroidManifestPackage>/BuildConfig.class
            //      element : BuildConfig
            "**/BuildConfig.class",

            // e.g. class   : <AndroidManifestPackage>/BR.class
            //      element : BR
            "**/BR.class",

            // e.g. class   : <AndroidManifestPackage>/DataBindingInfo.class
            //      element : DataBindingInfo
            "**/DataBindingInfo.class"

            //     "**/R.class",
            //     "**/R$*.class",
            //     "**/Manifest*.*",
            //     "android/**/*.*",
            //     "**/Lambda$*.class",
            //     "**/*\$Lambda$*.*",
            //     "**/Lambda.class",
            //     "**/*Lambda.class",
            //     "**/*Lambda*.class",
            //     "**/*Lambda*.*",
            //     "**/*Builder.*"
        )

        val javaClassDirectoriesTree = fileTree(
            mapOf(
                "dir" to "${buildDir}/intermediates/javac/$testVariant/classes/",
                "excludes" to classDirectoriesTreeExcludes
            )
        )

        val kotlinClassDirectoriesTree = fileTree(
            mapOf(
                "dir" to "${buildDir}/tmp/kotlin-classes/$testVariant",
                "excludes" to classDirectoriesTreeExcludes
            )
        )
        classDirectories.setFrom(files(javaClassDirectoriesTree, kotlinClassDirectoriesTree))

        val mainSourceDirectoryRelativePath = "src/main/java"
        val variantSourceDirectoryRelativePath = "src/$testVariant/java"
        sourceDirectories.setFrom(
            mainSourceDirectoryRelativePath,
            variantSourceDirectoryRelativePath
        )

        executionData.setFrom(
            fileTree(
                mapOf(
                    "dir" to project.projectDir,
                    "includes" to listOf(
                        "**/*.exec",
                        "**/*.ec"
                    )
                )
            )
        )
    }

}

kover {
    isDisabled = true
    generateReportOnCheck.set(true)
    instrumentAndroidPackage = false
}

releasesHub {
    pullRequestEnabled = true
    pullRequestsMax = 20
    pullRequestReviewers = listOf("RyuNen344")
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
        html.required.set(true)
        xml.required.set(true)
    }
}
