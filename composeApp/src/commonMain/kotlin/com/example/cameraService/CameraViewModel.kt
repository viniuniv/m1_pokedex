package com.example.cameraService

import androidx.lifecycle.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA

class CameraViewModel(
    private val permissionsController: PermissionsController,
) : ViewModel() {
    suspend fun requestCameraPermission(): Boolean {
        return try {
            permissionsController.providePermission(
                Permission.CAMERA
            )
            true
        } catch (e: DeniedException) {
            false
        } catch (e: DeniedAlwaysException) {
            false
        }
    }
}