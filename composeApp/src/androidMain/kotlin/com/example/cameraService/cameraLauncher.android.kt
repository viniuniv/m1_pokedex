package com.example.cameraService

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream

actual class cameraLauncher actual constructor(){
    private var onLaunch: (() -> Unit)? = null
    // Create a secondary constructor or a setter
    constructor(onLaunch: () -> Unit):this() {
        this.onLaunch = onLaunch
    }

    actual fun launch() {
        onLaunch?.invoke()
    }
}

@Composable
actual fun rememberCameraLauncher(onResult: (String?) -> Unit): cameraLauncher {
    val context = LocalContext.current

    // Using Android's native ActivityResult system wrapped for Compose
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            try {
                // Save the temporary bitmap thumbnail to your app's internal cache folder
                val file = File(context.cacheDir, "poke_capture_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }
                onResult(file.absolutePath) // Send the file path back to commonMain
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        } else {
            onResult(null) // Action was cancelled by the user
        }
    }

    return remember {
        cameraLauncher {
            launcher.launch()
        }
    }
}

@Composable
actual fun rememberBitmapFromPath(path: String?): ImageBitmap? {
    return remember(path) {
        if (path == null) return@remember null
        try {
            val file = File(path)
            if (file.exists()) {
                BitmapFactory.decodeFile(path)?.asImageBitmap()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


class AndroidImageSaver(
    private val context:Context
):ImageSaver{
    override suspend fun saveBitmap(bitmap: ImageBitmap?, fileName: String):String{
        val dir:File = context.filesDir
        val file = File(dir, fileName);
        bitmap?.asAndroidBitmap()?.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())
        return file.absolutePath;
    }
}