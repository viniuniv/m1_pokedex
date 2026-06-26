package com.example.pokedexpokeapi.platform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokemonGridItem
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex

@Composable
actual fun PokemonTeam(
    onHomeClick: ()->Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: ()-> Unit,

    onPokemonClick:(Int)->Unit,
    team: List<CaptureEntity>,
    pokemons:List<Pokemon>
){
    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "Meu Time"
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(team.size) { i ->
                val capture:CaptureEntity = team[i];
                val pokemon: Pokemon? = pokemons.find{it.id == capture.pokemonId};
                PokemonGridItem(
                    pokemon = pokemon!!,
                    onClick = { onPokemonClick(capture.pokemonId) }
                )
            }
        }
    }
}

