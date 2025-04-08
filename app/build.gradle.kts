plugins {
    // 版本繼承自 settings.gradle.kts
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.photo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.photo"
        minSdk = 21
        targetSdk = 33

        versionCode = 1
        versionName = "1.0"
    }

    // 啟用 Jetpack Compose
    buildFeatures {
        compose = true
    }

    // 指定 Compose 編譯器版本
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Compose 版本
    val composeUiVersion = "1.4.3"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    // Jetpack Compose Material (M2)
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.material:material:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")

    // 測試/除錯用
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
}
