package com.example.pokedexpokeapi.data.classes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class PokemonEntity(
    @PrimaryKey val id:Int,
    var name: String,
)
