package com.example.pokedexpokeapi.platform

import androidx.compose.runtime.Composable

@Composable
expect fun PokemonTeam(
    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,
    onPokemonClick: (Int) -> Unit
)