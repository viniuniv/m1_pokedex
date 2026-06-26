package com.example.pokedexpokeapi.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute
@Serializable
object PokedexRoute
@Serializable
object TeamRoute

@Serializable
data class PokemonDetailRoute(val pokemonId: Int)

@Serializable
data class CaptureRoute(val pokemonId: Int)

class Routes {
}
