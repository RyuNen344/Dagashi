plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    kotlin("plugin.serialization")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        applicationId = "com.ryunen344.dagashi"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.android.version.code.get().toInt()
        versionName = with(libs.versions.android.version) { "$major.$minor.$patch" }
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
            applicationIdSuffix = ".debug"
            buildConfigApiEndpoint("\"https://androiddagashi.github.io\"")
        }
        getByName("release") {
            isTestCoverageEnabled = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigApiEndpoint("\"https://androiddagashi.github.io\"")
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
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    packagingOptions {
        resources.excludes.add("META-INF/**")
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.serialization)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.emoji)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.browser)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.common.java8)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.androidx.room)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore)

    implementation(libs.groupie.core)
    implementation(libs.groupie.viewbinding)

    implementation(libs.coil)

    implementation(libs.corbind.core)
    implementation(libs.corbind.appcompat)
    implementation(libs.corbind.navigation)
    implementation(libs.corbind.recycler)
    implementation(libs.corbind.material)

    implementation(libs.ktor.client)
    implementation(libs.ktor.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger.android.navigation)
    kapt(libs.dagger.android.compiler)

    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")
    testImplementation("org.threeten:threetenbp:1.5.2")

    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.coroutine)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.room)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.kotlin.test.coroutine)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}

fun com.android.build.api.dsl.ApplicationBuildType.buildConfigApiEndpoint(endpoint: String) {
    buildConfigField("String", "API_ENDPOINT", endpoint)
}
