package com.example.cameraService

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

expect class cameraLauncher() {
    fun launch()
}

@Composable
expect fun rememberCameraLauncher(onResult: (String?) -> Unit): cameraLauncher



@Composable
expect fun rememberBitmapFromPath(path: String?): ImageBitmap?


interface ImageSaver{
    suspend fun saveBitmap(bitmap: ImageBitmap?, fileName: String):String?
}