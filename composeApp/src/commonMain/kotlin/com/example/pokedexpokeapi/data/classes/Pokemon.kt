package com.example.pokedexpokeapi.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpriteSlot(
    val front_default:String
)
@Serializable
data class PokemonStat(
    val base_stat:Int,
    val stat: Stat
)
@Serializable
data class Stat(
    val name:String
)

@Serializable
data class PokemonTypeSlot(
    val type: PokemonType
)
@Serializable
data class PokemonType(
    val name: String
)

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,

//    val imageUrl: String,
    val types: List<PokemonTypeSlot>,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>,
    val sprites:PokemonSpriteSlot
//    val description: String,
    )

