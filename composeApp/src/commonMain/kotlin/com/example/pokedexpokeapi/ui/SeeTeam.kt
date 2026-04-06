package com.example.pokedexpokeapi.ui

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
import androidx.compose.foundation.lazy.items

@Composable
fun PokemonTeam(
    onHomeClick: ()->Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: ()-> Unit,

    onPokemonClick:(Int)->Unit

){
    ScaffoldPokedex(
        onHomeClick=onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "Meu Time"
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(TimePokemon.size){i->
                val p = TimePokemon[i]
                PokemonGridItem(pokemon=p, onClick = {onPokemonClick(p.id)})
            }

        }

    }
}

val TimePokemon = mutableStateListOf<Pokemon>()