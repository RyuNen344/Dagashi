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
