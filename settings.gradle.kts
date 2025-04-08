pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        // Android Gradle Plugin
        id("com.android.application") version "8.1.1"

        // 假設使用 Kotlin 2.0.21 (請依實際需求調整)
        kotlin("android") version "2.0.21"

        // Kotlin Compose Plugin (在 Kotlin 2.0+ 開啟 Compose 必需)
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "photo"
include(":app")
