plugins {
    // 由 settings.gradle.kts 決定版本，此處只寫 plugin ID
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

    // 開啟 Compose
    buildFeatures {
        compose = true
    }

    // 指定 Compose Compiler 版本
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
    val composeUiVersion = "1.4.3"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    // 如果只想用 Compose Material (M2) 中的按鈕、文字等組件
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.material:material:$composeUiVersion") // M2
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")

    // 開發除錯用
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
}
