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
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import androidx.compose.runtime.collectAsState
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
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity
import com.example.pokedexpokeapi.ui.components.PokemonGridItem
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex

@Composable
fun PokedexGridScreen(
    viewModel: PokedexGridScreenViewModel,

    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,

    pokemons: List<Pokemon>,
    onPokemonClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    captureEntities: List<CaptureEntity>,
) {

    var pokemonFilters = remember { mutableStateListOf<String>() }
    var showFilterDialog by remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()

    val pokemonsToDisplay = remember(pokemons, pokemonFilters.size) {
        if (pokemonFilters.isEmpty()) {
            pokemons // Just use the original list if no filters
        } else {
            pokemons.filter { pokemon ->
                // Check if every type of the pokemon is included in the filters
                // Or change to .any if you want "at least one type matches"
                pokemon.types.all { typeSlot ->
                    pokemonFilters.contains(typeSlot.type.name)
                }
            }
        }
    }
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
    // 1. Monitor the last visible item index
    val lastVisibleItemIndex by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

// 2. Trigger whenever that index changes
    LaunchedEffect(lastVisibleItemIndex) {
        val totalItems = listState.layoutInfo.totalItemsCount

        // Threshold: Trigger when user sees an item within 5 of the end
        if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 6) {
            if (!state.isLoading) {
                println("Triggering load for index: $lastVisibleItemIndex")
                viewModel.loadMorePokemons()
            }
        }
    }

// 3. Special case: Trigger initial load if the list is empty
    LaunchedEffect(pokemons.size) {
        if (pokemons.isEmpty() && !state.isLoading) {
            viewModel.loadMorePokemons()
        }
    }

    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "PokeDex",

        ) {
        innerPadding ->
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
            modifier = Modifier.fillMaxSize(),
            // We ignore the bottom padding here to let items scroll behind the bar
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = 16.dp, // Use a small fixed value instead of the bar's height
                start = 8.dp,
                end = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),

            state = listState,

            ) {

            items(pokemonsToDisplay) { pokemon ->
                PokemonGridItem(
                    pokemon = pokemon,
                    onClick = { onPokemonClick(pokemon.id) }
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Layer 1: The Filter Button (Already here)
        FloatingActionButton(
            onClick = { showFilterDialog = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 90.dp, end = 16.dp) // Adjusted padding to fit your screen
        ) {
            Icon(imageVector = Icons.Filled.List, contentDescription = "Filter")
        }

        // Layer 2: The Loading Indicator (Floating above everything)
        if (state.isLoading) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Positions it at the bottom
                    .padding(bottom = 40.dp),      // Floats it exactly above the BottomBar icons
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}
