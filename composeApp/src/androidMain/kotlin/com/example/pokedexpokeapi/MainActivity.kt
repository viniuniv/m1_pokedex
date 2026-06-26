package com.example.pokedexpokeapi

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import com.example.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var continuation: Continuation<Boolean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)



        startKoin{
            modules(appModule)
            androidContext(this@MainActivity)
        }
        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            continuation?.resume(success)
            continuation = null
        }


        setContent {
            App(context=this)
        }
    }

    suspend fun launchCamera(uri: Uri): Boolean =
        suspendCoroutine { cont ->
            continuation = cont
            cameraLauncher.launch(uri)
        }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

