package com.example.photo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.combinedClickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// 資料類別：圖片資源 & 名稱
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
            MaterialTheme {
                ArtGalleryScreen()
            }
        }
    }
}

@Composable
fun ArtGalleryScreen() {
    val artworks = listOf(
        Artwork(0, R.drawable.artwork1, "作品名稱 1", "藝術家 A", "2020"),
        Artwork(1, R.drawable.artwork2, "作品名稱 2", "藝術家 B", "2019"),
        Artwork(2, R.drawable.artwork3, "作品名稱 3", "藝術家 C", "2021")
        // 如需更多，繼續加 artwork4, artwork5... etc
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]
    val context = LocalContext.current

    // 外層可偵測左右滑動
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < 0) {
                        // 向左滑 => Next
                        currentIndex =
                            if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
                    } else if (dragAmount > 0) {
                        // 向右滑 => Previous
                        currentIndex =
                            if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
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
            // 圖片
            Image(
                painter = painterResource(id = currentArtwork.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            // 顯示文字
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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

            Spacer(Modifier.height(24.dp))

            // 前後切換
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LongPressButton(
                    text = "Previous",
                    onClick = {
                        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
                    },
                    onLongPress = {
                        Toast.makeText(context, "長按：切換到上一張", Toast.LENGTH_SHORT).show()
                    }
                )

                LongPressButton(
                    text = "Next",
                    onClick = {
                        currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
                    },
                    onLongPress = {
                        Toast.makeText(context, "長按：切換到下一張", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

// ------------------- 重點：定義 LongPressButton --------------------------------

@OptIn(ExperimentalFoundationApi::class) // 若 IDE 提示需要實驗性API，可加此註解
@Composable
fun LongPressButton(
    text: String,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val indication = LocalIndication.current
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        color = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .combinedClickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick,
                onLongClick = onLongPress
            )
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
