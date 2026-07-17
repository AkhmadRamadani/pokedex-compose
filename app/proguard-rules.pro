# =============================================================================
# Pokedex - ProGuard / R8 rules
# =============================================================================
# These rules exist because release builds have isMinifyEnabled = true
# (see app/build.gradle.kts). Without them, reflection-based libraries
# (Moshi, Retrofit, Hilt) will crash at runtime once R8 renames/removes
# classes it thinks are unused.

# ---- Kotlin metadata / data classes ----------------------------------------
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod
-keep class kotlin.Metadata { *; }

# ---- Retrofit ----------------------------------------------------------------
-keepattributes Exceptions
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# ---- OkHttp --------------------------------------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**

# ---- Moshi -----------------------------------------------------------------
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepnames @com.squareup.moshi.JsonClass class *
-keep class com.example.pokedex.data.remote.dto.** { *; }
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**

# ---- Hilt / Dagger ---------------------------------------------------------
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager
-keepclasseswithmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# ---- Coroutines --------------------------------------------------------------
-dontwarn kotlinx.coroutines.flow.**FlowKt

# ---- Our own domain/data models (safe to keep verbatim; small, not a perf hit) --
-keep class com.example.pokedex.domain.model.** { *; }

# ---- Compose -----------------------------------------------------------------
-dontwarn androidx.compose.**
