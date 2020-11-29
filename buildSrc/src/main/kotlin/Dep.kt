object Dep {

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.1.1"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Android.Navigation.version}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Dagger.version}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
        const val releaseHub = "com.releaseshub:releases-hub-gradle-plugin:1.6.1"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:9.4.1"
    }

    object Android {
        const val core = "androidx.core:core-ktx:1.5.0-alpha05"
        const val compat = "androidx.appcompat:appcompat:1.3.0-alpha02"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val emoji = "androidx.emoji:emoji-appcompat:1.2.0-alpha01"
        const val material = "com.google.android.material:material:1.3.0-alpha03"
        const val activity = "androidx.activity:activity-ktx:1.2.0-beta01"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-beta01"
        const val recycler = "androidx.recyclerview:recyclerview:1.2.0-alpha06"
        const val preference = "androidx.preference:preference-ktx:1.1.1"
        const val browser = "androidx.browser:browser:1.3.0-rc01"

        object LifeCycle {
            private const val version = "2.3.0-beta01"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
        }

        object Navigation {
            const val version = "2.3.1"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Room {
            const val version = "2.3.0-alpha03"
            const val core = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object DataStore {
            private const val version = "1.0.0-alpha04"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Kotlin {
        const val version = "1.4.10"
        const val stdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val stdlibJDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

        object Coroutines {
            const val version = "1.4.1"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        object Serialization {
            private const val version = "1.0.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
        }
    }

    object Dagger {
        const val version = "2.28-alpha"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"

        object Android {
            private const val version = "1.0.0-alpha02"
            const val hilt = "androidx.hilt:hilt-common:$version"
            const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
        }
    }

    object Corbind {
        private const val version = "1.4.0"
        const val core = "ru.ldralighieri.corbind:corbind:$version"
        const val appcompat = "ru.ldralighieri.corbind:corbind-appcompat:$version"
        const val navigation = "ru.ldralighieri.corbind:corbind-navigation:$version"
        const val recycler = "ru.ldralighieri.corbind:corbind-recyclerview:$version"
        const val material = "ru.ldralighieri.corbind:corbind-material:$version"
    }

    object Ktor {
        private const val version = "1.4.2"
        const val client = "io.ktor:ktor-client-okhttp:$version"
        const val serialization = "io.ktor:ktor-client-serialization-jvm:$version"
    }

    object OkHttp {
        private const val version = "4.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Groupie {
        const val groupie = "com.xwray:groupie:2.8.0"
        const val viewbinding = "com.xwray:groupie-viewbinding:2.8.0"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:1.0.0"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object ThreeTen {
        const val android = "com.jakewharton.threetenabp:threetenabp:1.3.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.1"

        object Android {
            const val core = "androidx.test:core-ktx:1.3.0"
            const val junit = "androidx.test.ext:junit-ktx:1.1.2"
            const val room = "androidx.room:room-testing:${Dep.Android.Room.version}"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Kotlin {
            object Coroutines {
                const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Dep.Kotlin.Coroutines.version}"
            }
        }

        object Robolectric {
            const val robolectric = "org.robolectric:robolectric:4.4"
        }

        object Mockk {
            const val mock = "io.mockk:mockk:1.10.2"
        }
    }
}
