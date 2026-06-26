package com.example.pokedexpokeapi.data.classes.pokemon.captureRecord

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaptureEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val lat: Double,
    val long: Double,
    val photoPath: String,
    val pokemonId: Int
)
