buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dep.GradlePlugin.android)
        classpath(Dep.GradlePlugin.safeArgs)
        classpath(Dep.GradlePlugin.daggerHilt)
        classpath(Dep.GradlePlugin.kotlin)
        classpath(Dep.GradlePlugin.kotlinSerialization)
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
