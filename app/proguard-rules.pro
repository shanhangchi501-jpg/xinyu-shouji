# Keep serialization metadata for the local data model.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclassmembers class com.innerisle.app.data.** {
    *** Companion;
}
-keepclasseswithmembers class com.innerisle.app.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}
