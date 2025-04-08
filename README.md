# Android Photo Gallery App 開發紀錄
# 我提問
## 🧑‍💻 Context
你是一個 APP 開發的專家：

1. 我要開發一個 Android 應用程式，用於展示藝術品或家庭照片。
2. 使用技術：Android Studio、Kotlin DSL、Jetpack Compose。
3. 功能需求：
   - 展示圖片與相關資訊。
   - 按鈕可實現互動切換。
4. 系統環境：Linux。
5. 專案名稱：`photo`。
6. Package name：`com.example.photo`。

---

## 🎯 Objective

### 🔹 功能要求：
- [x] 至少要有十張圖
- [x] 每張圖要有說明（名稱、藝術家、年份）
- [x] 前一張、後一張功能
- [x] 第一張圖按 Previous 會跳至最後一張
- [x] 最後一張圖按 Next 會回到第一張圖
- [x] 支援滑動切換（Swipe 手勢）
- [x] 長按按鈕顯示 Tooltip
- [x] 可參考 Material 3 Compose 元件設計（https://material.io/blog/material-3-compose-1-1）

### 🔹 程式碼要求：

1. 使用 Jetpack Compose 設計畫面：
   - 1 張圖片（`Image`）
   - 2-3 個文字（`Text`）：藝術品名稱、藝術家、年份
   - 2 個按鈕（`Button`）：`Previous` 與 `Next`
   - 使用 `Row` 與 `Column` 佈局
   - 圖片在上方，文字在中間，按鈕在下方
   - 使用 `Modifier` 設計 UI 排版（padding、alignment 等）

2. 資料與互動邏輯：
   - 定義藝術品資料類別（ID、圖片、名稱、藝術家、年份）
   - 提供 3 筆範例資料
   - 使用 `MutableState` 追蹤目前顯示的藝術品
   - 按鈕實作：
     - Next：若為最後一張圖，回到第一張
     - Previous：若為第一張圖，跳至最後一張
     - 使用 `when` 語句處理按鈕切換邏輯

3. 程式碼需求：
   - 提供 `MainActivity.kt` 完整 `@Composable` 函數
   - 提供必要的 `Gradle` 設定與 `import` 區段

---

# AI答
使用 Android Studio (Kotlin DSL) + Jetpack Compose 建立藝術作品展示 App
本專案展示了如何使用 Jetpack Compose 製作一個圖片輪播展示應用，包含：

完整專案結構與 Gradle Kotlin DSL 設定

10 張圖片的範例 Artwork 資料

圖片展示 + 上下張切換（含滑動、長按 Tooltip）

UI 使用 Material3 設計

📁 專案結構與檔案配置
1. settings.gradle.kts
kotlin
複製
編輯
rootProject.name = "photo"
include(":app")
2. app/build.gradle.kts
kotlin
複製
編輯
plugins {
    id("com.android.application")
    kotlin("android")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {
    val composeUiVersion = "1.3.3"
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
}
3. MainActivity.kt
kotlin
複製
編輯
package com.example.photo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class Artwork(
    val id: Int,
    val imageRes: Int,
    val name: String,
    val artist: String,
    val year: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryScreen()
        }
    }
}

@Composable
fun ArtGalleryScreen() {
    val artworks = listOf(
        Artwork(0, R.drawable.artwork1, "作品名稱 1", "藝術家 A", "2020"),
        Artwork(1, R.drawable.artwork2, "作品名稱 2", "藝術家 B", "2019"),
        Artwork(2, R.drawable.artwork3, "作品名稱 3", "藝術家 C", "2021"),
        Artwork(3, R.drawable.artwork4, "作品名稱 4", "藝術家 D", "2018"),
        Artwork(4, R.drawable.artwork5, "作品名稱 5", "藝術家 E", "2017"),
        Artwork(5, R.drawable.artwork6, "作品名稱 6", "藝術家 F", "2022"),
        Artwork(6, R.drawable.artwork7, "作品名稱 7", "藝術家 G", "2023"),
        Artwork(7, R.drawable.artwork8, "作品名稱 8", "藝術家 H", "2021"),
        Artwork(8, R.drawable.artwork9, "作品名稱 9", "藝術家 I", "2020"),
        Artwork(9, R.drawable.artwork10, "作品名稱 10", "藝術家 J", "2015"),
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < 0) {
                        currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
                    }
                    if (dragAmount > 0) {
                        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = currentArtwork.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentArtwork.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "藝術家：${currentArtwork.artist}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "年份：${currentArtwork.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                Toast.makeText(context, "回到上一張圖片", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = {
                        currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                Toast.makeText(context, "切換到下一張圖片", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                ) {
                    Text("Next")
                }
            }
        }
    }
}
💡 程式重點整理
Artwork 資料類別：包含 id, imageRes, name, artist, year。

MutableState 管理 currentIndex：使用 remember { mutableStateOf(0) }。

按鈕切換邏輯：

Previous：若為第一張，跳轉到最後一張。

Next：若為最後一張，回到第一張。

滑動手勢切換圖片：

向左滑 → 下一張

向右滑 → 前一張

長按顯示 Tooltip：

使用 pointerInput + detectTapGestures(onLongPress = { ... })。

顯示 Toast 訊息提示使用者。

📷 注意事項
請將圖片檔放入 res/drawable/ 中，命名為 artwork1 至 artwork10。

R.drawable.artworkX 對應的是這些圖檔。

適用於教學展示、照片展示牆、藝術作品介紹 App 等場景。

祝開發順利！🚀
---
# 我問
Starting in Kotlin 2.0, the Compose Compiler Gradle plugin is required
when compose is enabled. See the following link for more information:
https://d.android.com/r/studio-ui/compose-compiler

解決這個套用的問題，請直接傳給我完整的程式碼，我是使用Kotlin DSL

# AI debug...(skip)
---
# 我問
問題出在<resources xmlns:tools="http://schemas.android.com/tools">

    <!-- 自定義主題，改用 MaterialComponents (M2) -->
    <style name="Theme.Photo" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <!-- 可以在此自訂色彩等屬性 -->
        <item name="android:windowEnableSplitTouch">false</item>

        <!-- Example: 自行客製一些屬性
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryDark">@color/purple_700</item>
        <item name="colorAccent">@color/teal_200</item>
        -->
    </style>

</resources>

ERROR: AAPT: error: resource style/Theme.MaterialComponents.DayNight.NoActionBar (aka com.example.photo:style/Theme.MaterialComponents.DayNight.NoActionBar) not found.
error: failed linking references.

我記得可以不要用theme.xml 換一個不會出包的
# AI debug...(skip)
簡略來說：換成Materia2 刪掉theme and style
---
# 我問
ERROR: /home/kali/AndroidStudioProjects/photo/app/src/main/res/layout/activity_main.xml:17: AAPT: error: attribute layout_constraintTop_toTopOf (aka com.example.photo:layout_constraintTop_toTopOf) not found.
# AI debug...(skip)
簡略來說：刪掉app/src/main/res/layout/activity_main.xml
---
# 我問
長按沒有跳出文字，我透過logcat判斷確定沒有被偵測到，非常確定，請優化程式碼 
# AI debug...(skip)
簡略來說：外層手勢衝突
以 combinedClickable 替代了「Button + pointerInput」，大幅降低手勢衝突。
按鈕「短按」與「長按」皆可分開執行邏輯，長按事件中顯示 Toast，保證在大多數情況都能正常偵測。
