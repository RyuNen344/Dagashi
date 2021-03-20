object Dep {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.1.2"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Android.Navigation.version}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Dagger.version}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
        const val releaseHubVersion = "1.7.0"
        const val releaseHub = "com.releaseshub:releases-hub-gradle-plugin:$releaseHubVersion"
        const val ktlintVersion = "9.4.1"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion"
    }

    object Android {
        const val core = "androidx.core:core-ktx:1.5.0-alpha05"
        const val compat = "androidx.appcompat:appcompat:1.3.0-alpha02"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val emoji = "androidx.emoji:emoji-appcompat:1.2.0-alpha01"
        const val material = "com.google.android.material:material:1.3.0"
        const val activity = "androidx.activity:activity-ktx:1.2.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.1"
        const val recycler = "androidx.recyclerview:recyclerview:1.2.0-alpha06"
        const val browser = "androidx.browser:browser:1.3.0"

        object LifeCycle {
            private const val version = "2.3.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
        }

        object Navigation {
            const val version = "2.3.3"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Room {
            const val version = "2.3.0-beta01"
            const val core = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object DataStore {
            private const val version = "1.0.0-alpha04"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Kotlin {
        const val version = "1.4.31"
        const val stdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val stdlibJDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
        }

        object Serialization {
            const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"
        }
    }

    object Dagger {
        const val version = "2.30.1-alpha"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"

        object Android {
            private const val version = "1.0.0-alpha02"
            const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
        }

        object Assisted {
            private const val version = "0.6.0"
            const val common = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
            const val compiler = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
        }
    }

    object Corbind {
        const val core = "ru.ldralighieri.corbind:corbind:1.5.0"
        const val appcompat = "ru.ldralighieri.corbind:corbind-appcompat:1.5.0"
        const val navigation = "ru.ldralighieri.corbind:corbind-navigation:1.5.0"
        const val recycler = "ru.ldralighieri.corbind:corbind-recyclerview:1.5.0"
        const val material = "ru.ldralighieri.corbind:corbind-material:1.5.0"
    }

    object Ktor {
        const val client = "io.ktor:ktor-client-okhttp:1.4.2"
        const val serialization = "io.ktor:ktor-client-serialization-jvm:1.4.2"
    }

    object OkHttp {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object Groupie {
        const val groupie = "com.xwray:groupie:2.9.0"
        const val viewbinding = "com.xwray:groupie-viewbinding:2.9.0"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:1.1.1"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object ThreeTen {
        const val android = "com.jakewharton.threetenabp:threetenabp:1.3.0"
        const val jvm = "org.threeten:threetenbp:1.5.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"

        object Android {
            const val core = "androidx.test:core-ktx:1.3.0"
            const val junit = "androidx.test.ext:junit-ktx:1.1.2"
            const val room = "androidx.room:room-testing:${Dep.Android.Room.version}"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Kotlin {
            object Coroutines {
                const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3"
            }
        }

        object Robolectric {
            const val robolectric = "org.robolectric:robolectric:4.5.1"
        }

        object Mockk {
            const val mock = "io.mockk:mockk:1.10.6"
        }
    }
}
