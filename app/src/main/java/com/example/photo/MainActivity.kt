package com.example.photo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// 資料類別：圖片資源、名稱、藝術家、年份
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
            // 只使用 Compose，不引用傳統 XML layout
            MaterialTheme {
                ArtGalleryScreen()
            }
        }
    }
}

@Composable
fun ArtGalleryScreen() {
    // 準備 10 張圖
    val artworks = listOf(
        Artwork(0, R.drawable.artwork1,  "作品名稱 1", "藝術家 A", "2020"),
        Artwork(1, R.drawable.artwork2,  "作品名稱 2", "藝術家 B", "2019"),
        Artwork(2, R.drawable.artwork3,  "作品名稱 3", "藝術家 C", "2021"),
        Artwork(3, R.drawable.artwork4,  "作品名稱 4", "藝術家 D", "2018"),
        Artwork(4, R.drawable.artwork5,  "作品名稱 5", "藝術家 E", "2017"),
        Artwork(5, R.drawable.artwork6,  "作品名稱 6", "藝術家 F", "2022"),
        Artwork(6, R.drawable.artwork7,  "作品名稱 7", "藝術家 G", "2023"),
        Artwork(7, R.drawable.artwork8,  "作品名稱 8", "藝術家 H", "2021"),
        Artwork(8, R.drawable.artwork9,  "作品名稱 9", "藝術家 I", "2020"),
        Artwork(9, R.drawable.artwork10, "作品名稱 10","藝術家 J", "2015"),
    )

    // 追蹤當前顯示的索引
    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    val context = LocalContext.current

    // 監聽左右滑動 (Swiping)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < 0) {
                        // 向左滑 => 下一張
                        currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
                    } else if (dragAmount > 0) {
                        // 向右滑 => 前一張
                        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
                    }
                }
            }
    ) {
        // 佈局：圖片在上方、文字在中間、按鈕在下方
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // 圖片
            Image(
                painter = painterResource(id = currentArtwork.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // 文字區（名稱、藝術家、年份）
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentArtwork.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "藝術家：${currentArtwork.artist}",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "年份：${currentArtwork.year}",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // 前 / 後 按鈕
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Previous
                Button(
                    onClick = {
                        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
                    },
                    // 長按 => Toast 作為簡易 tooltip
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
                // Next
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
