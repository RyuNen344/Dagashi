object Dep {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.2.1"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:2.37"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:1.5.20"
        const val releaseHubVersion = "2.0.2"
        const val releaseHub = "com.dipien:releases-hub-gradle-plugin:2.0.2"
        const val ktlintVersion = "10.1.0"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:10.1.0"
    }

    object Android {
        const val core = "androidx.core:core-ktx:1.5.0"
        const val compat = "androidx.appcompat:appcompat:1.3.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val emoji = "androidx.emoji:emoji-appcompat:1.2.0-alpha01"
        const val material = "com.google.android.material:material:1.3.0"
        const val activity = "androidx.activity:activity-ktx:1.2.3"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
        const val recycler = "androidx.recyclerview:recyclerview:1.2.1"
        const val browser = "androidx.browser:browser:1.3.0"

        object LifeCycle {
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
        }

        object Navigation {
            const val fragment = "androidx.navigation:navigation-fragment-ktx:2.3.5"
            const val ui = "androidx.navigation:navigation-ui-ktx:2.3.5"
        }

        object Room {
            const val core = "androidx.room:room-ktx:2.3.0"
            const val compiler = "androidx.room:room-compiler:2.3.0"
        }

        object DataStore {
            private const val version = "1.0.0-alpha04"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Kotlin {
        const val stdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib:1.5.20"
        const val stdlibJDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.20"

        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
        }

        object Serialization {
            const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.1"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"
        }
    }

    object Dagger {
        const val hilt = "com.google.dagger:hilt-android:2.37"
        const val compiler = "com.google.dagger:hilt-android-compiler:2.37"

        object Android {
            const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
        }
    }

    object Corbind {
        const val core = "ru.ldralighieri.corbind:corbind:1.5.2"
        const val appcompat = "ru.ldralighieri.corbind:corbind-appcompat:1.5.2"
        const val navigation = "ru.ldralighieri.corbind:corbind-navigation:1.5.1"
        const val recycler = "ru.ldralighieri.corbind:corbind-recyclerview:1.5.1"
        const val material = "ru.ldralighieri.corbind:corbind-material:1.5.1"
    }

    object Ktor {
        const val client = "io.ktor:ktor-client-okhttp:1.6.0"
        const val serialization = "io.ktor:ktor-client-serialization-jvm:1.6.0"
    }

    object OkHttp {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object Groupie {
        const val groupie = "com.xwray:groupie:2.9.0"
        const val viewbinding = "com.xwray:groupie-viewbinding:2.9.0"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:1.2.2"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object ThreeTen {
        const val android = "com.jakewharton.threetenabp:threetenabp:1.3.1"
        const val jvm = "org.threeten:threetenbp:1.5.1"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"

        object Android {
            const val core = "androidx.test:core-ktx:1.3.0"
            const val junit = "androidx.test.ext:junit-ktx:1.1.2"
            const val room = "androidx.room:room-testing:2.3.0"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Kotlin {
            object Coroutines {
                const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"
            }
        }

        object Robolectric {
            const val robolectric = "org.robolectric:robolectric:4.5.1"
        }

        object Mockk {
            const val mock = "io.mockk:mockk:1.11.0"
        }
    }
}
