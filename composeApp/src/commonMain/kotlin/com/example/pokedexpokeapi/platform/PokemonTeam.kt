package com.example.pokedexpokeapi.platform

import androidx.compose.runtime.Composable
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity

@Composable
expect fun PokemonTeam(
    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,
    onPokemonClick: (Int) -> Unit,
    team: List<CaptureEntity>,
    pokemons:List<Pokemon>
)
