package com.example.cameraService

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

actual class cameraLauncher actual constructor(){
    actual fun launch() {
        TODO("Not yet implemented")
    }


}

@Composable
actual fun rememberCameraLauncher(onResult: (String?) -> Unit): cameraLauncher {
    TODO("Not yet implemented")
}

@Composable
actual fun rememberBitmapFromPath(path: String?): ImageBitmap? {
    TODO("Not yet implemented")
}

actual fun saveBitmap(bitmap: ImageBitmap, fileName: String):String{
    TODO()
}
