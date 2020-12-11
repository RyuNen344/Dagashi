plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("jacoco")
    id("com.releaseshub.gradle.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    compileSdkVersion(Versions.androidCompileSdkVersion)
    buildToolsVersion(Versions.androidBuildToolsVersion)

    defaultConfig {
        applicationId = Packages.name
        minSdkVersion(Versions.androidMinSdkVersion)
        targetSdkVersion(Versions.androidTargetSdkVersion)
        versionCode = Versions.androidVersionCode
        versionName = Versions.androidVersionName
        javaCompileOptions {
            annotationProcessorOptions {
                arguments.putAll(
                    mutableMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
            applicationIdSuffix = Packages.debugNameSuffix
            buildConfigField("String", "API_ENDPOINT", "\"https://androiddagashi.github.io\"")
        }
        getByName("release") {
            isTestCoverageEnabled = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_ENDPOINT", "\"https://androiddagashi.github.io\"")
        }
    }

    sourceSets {
        val sharedTestDir = "src/sharedTest/java"
        getByName("androidTest").java.srcDirs(sharedTestDir)
        getByName("test").java.srcDirs(sharedTestDir)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    lintOptions {
        disable += setOf(
            "UnsafeExperimentalUsageError",
            "UnsafeExperimentalUsageWarning"
        )
    }
    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(Dep.Kotlin.stdlibCommon)
    implementation(Dep.Kotlin.stdlibJDK8)
    implementation(Dep.Kotlin.Coroutines.android)
    implementation(Dep.Kotlin.Serialization.core)
    implementation(Dep.Kotlin.Serialization.json)

    implementation(Dep.Android.compat)
    implementation(Dep.Android.core)
    implementation(Dep.Android.constraintLayout)
    implementation(Dep.Android.emoji)
    implementation(Dep.Android.material)
    implementation(Dep.Android.activity)
    implementation(Dep.Android.fragment)
    implementation(Dep.Android.recycler)
    implementation(Dep.Android.preference)
    implementation(Dep.Android.browser)

    implementation(Dep.Android.LifeCycle.viewModel)
    implementation(Dep.Android.LifeCycle.liveData)
    implementation(Dep.Android.LifeCycle.commonJava8)

    implementation(Dep.Android.Navigation.fragment)
    implementation(Dep.Android.Navigation.ui)

    implementation(Dep.Groupie.groupie)
    implementation(Dep.Groupie.viewbinding)

    implementation(Dep.Coil.coil)

    implementation(Dep.Corbind.core)
    implementation(Dep.Corbind.appcompat)
    implementation(Dep.Corbind.navigation)
    implementation(Dep.Corbind.recycler)
    implementation(Dep.Corbind.material)

    implementation(Dep.Android.Room.core)
    kapt(Dep.Android.Room.compiler)

    implementation(Dep.Android.DataStore.preferences)

    implementation(Dep.Ktor.client)
    implementation(Dep.Ktor.serialization)
    implementation(Dep.OkHttp.loggingInterceptor)

    implementation(Dep.Dagger.hilt)
    kapt(Dep.Dagger.compiler)
    implementation(Dep.Dagger.Android.hilt)
    implementation(Dep.Dagger.Android.viewModel)
    kapt(Dep.Dagger.Android.compiler)

    implementation(Dep.ThreeTen.android)

    implementation(Dep.Timber.timber)

    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.Kotlin.Coroutines.test)
    testImplementation(Dep.Test.Android.core)
    testImplementation(Dep.Test.Android.junit)
    testImplementation(Dep.Test.Android.room)
    testImplementation(Dep.Test.Robolectric.robolectric)
    testImplementation(Dep.Test.Mockk.mock)
    testImplementation(Dep.ThreeTen.jvm)
    androidTestImplementation(Dep.Test.Kotlin.Coroutines.test)
    androidTestImplementation(Dep.Test.Android.junit)
    androidTestImplementation(Dep.Test.Android.espresso)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.suppressWarnings = false
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
        "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi"
    )
}

jacoco {
    toolVersion = "0.8.5"
}

task("jacocoMerge", JacocoMerge::class) {
    gradle.afterProject {
        if (project.rootProject != project && project.plugins.hasPlugin("jacoco")) {
            executionData = files("${project.buildDir}/jacoco/testDebugUnitTest.exec")
        }
    }
}

task("jacocoTestReport", JacocoReport::class) {
    dependsOn(tasks.getByName("jacocoMerge"))
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
    }
    executionData.from += (tasks.getByName("jacocoMerge") as JacocoMerge).destinationFile

    gradle.afterProject {
        if (project.rootProject != project && project.plugins.hasPlugin("jacoco")) {
            sourceDirectories.setFrom("${project.projectDir}/src/main/java")
            classDirectories.setFrom(
                project.fileTree(
                    "${project.buildDir}/tmp/kotlin-classes/debug"
                )
            )
        }
    }
}

releasesHub {
    dependenciesBasePath = "buildSrc/src/main/kotlin/"
    dependenciesClassNames = listOf("Dep.kt")
    pullRequestEnabled = true
    gitHubRepositoryOwner = "RyuNen344"
    gitHubRepositoryName = "Dagashi"
    pullRequestsMax = 2
    gitHubUserName = "RyuNen344"
    gitHubUserEmail = "s1100633@outlook.com"
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
