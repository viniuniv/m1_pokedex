package com.example.locationService

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class AndroidLocationService(
    private val context: Context,
) : LocationButton {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")//funcionando normalmente?
    override suspend fun getCurrentLocation(): DeviceLocation? =
        suspendCancellableCoroutine { cont ->

            client.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(
                            DeviceLocation(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    } else {
                        cont.resume(null)
                    }
                }.addOnFailureListener {
                    cont.resume(null)
                }
        }
}