package com.example.locationService



data class DeviceLocation(
    val latitude: Double,
    val longitude: Double
)

interface LocationButton {
    suspend fun getCurrentLocation(): DeviceLocation?
}