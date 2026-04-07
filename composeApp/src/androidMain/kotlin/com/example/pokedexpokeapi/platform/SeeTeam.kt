package com.example.pokedexpokeapi.platform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexpokeapi.data.Pokemon
import com.example.pokedexpokeapi.ui.TimePokemon

@Composable
actual fun PokemonTeam(
    onHomeClick: ()->Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: ()-> Unit,

    onPokemonClick:(Int)->Unit

){
    _root_ide_package_.com.example.pokedexpokeapi.ui.ScaffoldPokedex(
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
            items(TimePokemon.size) { i ->
                val p = TimePokemon[i]
                _root_ide_package_.com.example.pokedexpokeapi.ui.PokemonGridItem(
                    pokemon = p,
                    onClick = { onPokemonClick(p.id) })
            }

        }

    }
}

