package com.example.pokedexpokeapi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokedexpokeapi.data.Pokemon
import kotlin.collections.contains

@Composable
fun PokedexGridScreen(
    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,

    pokemons: List<Pokemon>,
    onPokemonClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "PokéDex"
    ) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,

            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pokemons) { pokemon ->
                PokemonGridItem(
                    pokemon = pokemon,
                    onClick = { onPokemonClick(pokemon.id) }
                )
            }
        }

    }


}

@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = corTipoPokemon(pokemon.types[0])
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(TimePokemon.contains(pokemon)){
                Icon(
                    modifier = Modifier.align(Alignment.End),
                    tint = Color(0x006400),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "er"

                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name
                )
            }

            Text(
                text = pokemon.id.formatPokemonNumber(),
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = pokemon.name.capitalizePokemonName(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 6.dp)
            )

            Column(
                modifier = Modifier.padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pokemon.types.forEach { type ->
                    AssistChip(
                        onClick = {},
                        label = { Text(type.capitalizePokemonName()) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)

                    )
                }
            }
        }
    }
}
@Composable
fun corTipoPokemon(tipo:String):Color{
    when(tipo){
        "water" -> return Color(0xff93c3ca);
        "poison"-> return Color(0xff35612a);
        "flying"->return Color(0xffcedaa2);
        "grass"-> return Color(0xff9ed590);
        "fire" -> return Color(0xffeab17b);
        "electric" -> return Color(0xfff3ed96);
        "fairy"->return Color(0xfff4b4b4);
        "normal"->return Color(0xffe6e6e6)
    }
    return Color(0xff000000)
}
val TimePokemon = mutableStateListOf<Pokemon>()