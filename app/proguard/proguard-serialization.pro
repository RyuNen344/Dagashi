#see https://github.com/Kotlin/kotlinx.serialization#android

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.ryunen344.dagashi.data.api.response.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.ryunen344.dagashi.data.api.response.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.ryunen344.dagashi.data.api.response.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}
