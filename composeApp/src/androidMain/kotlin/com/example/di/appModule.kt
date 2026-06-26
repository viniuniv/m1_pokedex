package com.example.di

import com.example.cameraService.AndroidImageSaver
import com.example.cameraService.ImageSaver
import com.example.locationService.AndroidLocationService
import com.example.locationService.LocationButton
import org.koin.dsl.module

val appModule = module {

    single<LocationButton> {
        AndroidLocationService(get())
    }
    single<ImageSaver> {
        AndroidImageSaver(get())
    }


}