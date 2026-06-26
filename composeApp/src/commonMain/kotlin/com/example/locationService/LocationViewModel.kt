package com.example.locationService

import androidx.lifecycle.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.location.LOCATION


class LocationViewModel(
    private val permissionsController: PermissionsController,
) : ViewModel() {
    suspend fun requestLocationPermission(): Boolean {
        return try {
            permissionsController.providePermission(
                Permission.LOCATION
            )
            true
        } catch (e: DeniedException) {
            false
        } catch (e: DeniedAlwaysException) {
            false
        }
    }

    suspend fun hasLocationPermission():Boolean {
        return permissionsController.isPermissionGranted(Permission.LOCATION)
    }
}