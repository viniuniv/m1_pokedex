package com.example.pokedexpokeapi.ui.CaptureScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cameraService.CameraViewModel
import com.example.cameraService.ImageSaver
import com.example.cameraService.rememberBitmapFromPath
import com.example.cameraService.rememberCameraLauncher
import com.example.locationService.DeviceLocation
import com.example.locationService.LocationButton
import com.example.locationService.LocationViewModel
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.Capture
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureDao
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun CaptureScreen(
    viewModel: CaptureScreenViewModel,
    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,
    dao: CaptureDao,
    permissionsController: PermissionsController,
    pokemon: Pokemon?,
    onCaptureFinished:()->Unit
) {
    val scope = rememberCoroutineScope();


    val locationService: LocationButton = koinInject<LocationButton>()
    val imageSaver: ImageSaver = koinInject<ImageSaver>();

    val locationViewModel: LocationViewModel = LocationViewModel(permissionsController);

    var locationPermissionGranted by remember { mutableStateOf(false) }
    var deviceLocation by remember { mutableStateOf<DeviceLocation?>(null) }

    var cameraPermissionGranted by remember { mutableStateOf(false) }
    var bitmap: ImageBitmap? by remember { mutableStateOf(null) }

    // 1. Create a state variable to hold the path of the taken picture
    var capturedImagePath by remember { mutableStateOf<String?>(null) }

    // 2. Initialize your multiplatform camera launcher
    val cameraLauncher = rememberCameraLauncher { path ->
        if (path != null) {
            capturedImagePath = path // Storing the absolute file path of the photo
        }
    }
    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "Capturar " + pokemon?.name?.capitalize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (capturedImagePath != null) {

                bitmap = rememberBitmapFromPath(capturedImagePath)

                if (bitmap != null) {
                    Box(
                        modifier = Modifier.wrapContentSize()
                            .border(4.dp, Color(0xFFD0BCFF), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = bitmap!!,
                            contentDescription = "Foto capturada",
                            modifier = Modifier
                                .size(400.dp)
                                .rotate(90.toFloat()),
                            contentScale = ContentScale.Crop

                        )
                        AsyncImage(
                            model = pokemon?.sprites?.front_default,
                            contentDescription = pokemon?.name,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                } else {
                    Text("Erro ao carregar imagem")
                }
            } else {
                AsyncImage(
                    model = pokemon?.sprites?.front_default,
                    contentDescription = pokemon?.name,
                    modifier = Modifier.size(150.dp)
                )
            }
            if (deviceLocation == null) {
                Button(
                    onClick = {
                        scope.launch {
                            locationPermissionGranted =
                                locationViewModel.requestLocationPermission()
                            if (locationPermissionGranted) {
                                deviceLocation = locationService.getCurrentLocation()
                            }
                        }
                        viewModel.setLoading(true)
                    }
                ) {
                    Text("Registrar Local")
                }
            }
            if (!locationPermissionGranted) {
                Text("Para regitrar a captura, conceda acesso ao GPS de seu dispositivo.")
            }
            if (deviceLocation != null) {
                Text("Capturado em:\n ${deviceLocation?.latitude}, ${deviceLocation?.longitude}")
            }

            Button(
                onClick = {
                    scope.launch {
                        val viewModel = CameraViewModel(permissionsController)
                        cameraPermissionGranted = viewModel.requestCameraPermission()

                        if (cameraPermissionGranted) {
                            cameraLauncher.launch()
                        }
                    }

                }
            ) {
                Text("Fotografar Captura")
            }
            if (!cameraPermissionGranted) {
                Text("Para registrar a captura, conceda acesso ao sistema de captura de imagens.")
            }

            Button(
                enabled = deviceLocation != null && pokemon != null && bitmap != null,
                onClick = {
                    scope.launch {

                        val savedImagePath =
                            imageSaver.saveBitmap(bitmap, "poke_capture_${pokemon?.name}.jpg")
                        val capture = Capture(
                            lat = deviceLocation!!.latitude,
                            long = deviceLocation!!.longitude,
                            photoPath = savedImagePath!!,
                            pokemonId = pokemon!!.id
                        )
                        dao.registerCapture(
                            capture = capture
                        )
                    }
                    onCaptureFinished()

                }
            ) { Text("Registrar Captura") }
        }
    }
}

