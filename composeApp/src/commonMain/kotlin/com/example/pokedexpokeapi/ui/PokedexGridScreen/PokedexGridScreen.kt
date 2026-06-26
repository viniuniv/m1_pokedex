package com.example.pokedexpokeapi.ui.PokedexGridScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.ui.capitalizePokemonName
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex
import com.example.pokedexpokeapi.ui.formatPokemonNumber

@Composable
fun PokedexGridScreen(
    viewModel: PokedexGridScreenViewModel,

    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,

    pokemons: List<Pokemon>,
    onPokemonClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
) {
    var pokemonFilters = remember { mutableStateListOf<String>() }
    var showFilterDialog by remember { mutableStateOf(false) }

    val typeFilterOptions =
        listOf(
            "water",
            "poison",
            "flying",
            "grass",
            "fire",
            "electric",
            "fairy",
            "normal",
            "bug",
            "ground",
            "fighting",
            "psychic",
            "rock",
            "ghost",
            "ice",
            "dragon"
        )

    val listState = rememberLazyGridState();
    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount;
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisibleIndex > (totalItemsNumber - 3) || totalItemsNumber == 0
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "PokéDex",

        ) {
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Selectione os tipos para filtrar") },
                text = {
                    Column {
                        typeFilterOptions.forEach { option ->
                            Text(
                                text = option,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (pokemonFilters.contains(option)) {
                                            pokemonFilters.remove(option)
                                        } else {
                                            pokemonFilters.add(option)
                                        }
                                    }.padding(12.dp)
                            )
                        }
                    }
                },
                confirmButton = {}
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,

            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),

            state = listState,

            ) {

            items(pokemons) { pokemon ->
                if (pokemonFilters.isEmpty()) {
                    PokemonGridItem(
                        pokemon = pokemon,
                        onClick = { onPokemonClick(pokemon.id) }
                    )
                } else {
                    var filterPassed = true;
                    for (type in pokemon.types) {
                        print(type.type.name)
                        print("\n")
                        if (!pokemonFilters.contains(type.type.name)) {
                            print("notcoontai?")
                            filterPassed = false
                        }
                    }
                    if (filterPassed) {
                        PokemonGridItem(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon.id) }
                        )
                    }
                }
            }
        }

    }
    Box(
            modifier = Modifier
                .fillMaxSize()

            ){
        FloatingActionButton(
            onClick = {
                        showFilterDialog = true
                      },
            modifier = Modifier.align(
                Alignment.BottomEnd
            ).padding(16.dp)
        )
        {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "er"
            )
        }
    }

}


@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = corTipoPokemon(pokemon.types[0].type.name)
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (TimePokemon.contains(pokemon)) {
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
                    model = pokemon.sprites.front_default,
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
                pokemon.types.forEach { typeSlot ->
                    AssistChip(
                        onClick = {},
                        label = { Text(typeSlot.type.name.capitalizePokemonName()) },
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
fun corTipoPokemon(tipo: String): Color {
    when (tipo) {
        "water" -> return Color(0xff93c3ca);
        "poison" -> return Color(0xff35612a);
        "flying" -> return Color(0xffcedaa2);
        "grass" -> return Color(0xff9ed590);
        "fire" -> return Color(0xffeab17b);
        "electric" -> return Color(0xfff3ed96);
        "fairy" -> return Color(0xfff4b4b4);
        "normal" -> return Color(0xffe6e6e6);
        "bug" -> return Color(0xffb2f0a7)
        "ground" -> return Color(0xffdedc79)
        "fighting" -> return Color(0xfff6ffa1)
        "psychic" -> return Color(0xffdba8da)
        "rock" -> return Color(0xffdcdcdc)
        "ghost" -> return Color(0xffc2aec1)
        "ice" -> return Color(0xffc2d3eb)
        "dragon" -> return Color(0xfff0cf9b)
    }
    return Color(0xff000000)
}

val TimePokemon = mutableStateListOf<Pokemon>()