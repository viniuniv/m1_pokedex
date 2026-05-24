package com.example.pokedexpokeapi


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokedexpokeapi.data.PokemonMock
import com.example.pokedexpokeapi.navigation.HomeRoute
import com.example.pokedexpokeapi.navigation.PokedexRoute
import com.example.pokedexpokeapi.navigation.PokemonDetailRoute
import com.example.pokedexpokeapi.navigation.TeamRoute
import com.example.pokedexpokeapi.platform.PokemonTeam
import com.example.pokedexpokeapi.ui.HomeScreen.HomeScreen
import com.example.pokedexpokeapi.ui.HomeScreen.HomeViewModel
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreen
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreenViewModel
import com.example.pokedexpokeapi.ui.PokemonDetailScreen.PokemonDetailScreen
import com.example.pokedexpokeapi.ui.PokemonDetailScreen.PokemonDetailScreenViewModel


@Composable
@Preview
fun App() {
    val homeViewModel = viewModel<HomeViewModel>()
    val pokedexGridScreenViewModel = viewModel<PokedexGridScreenViewModel>()
    val pokemonDetailScreenViewModel = viewModel<PokemonDetailScreenViewModel>()
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    viewModel = homeViewModel,
                    onSeePokedexClick = {
                        navController.navigate(PokedexRoute)
                    },
                    onHomeClick = {
                        navController.navigate(HomeRoute)
                    },
                    onSeeTeamClick = {
                        navController.navigate(TeamRoute)
                    }

                )
            }

            composable<PokedexRoute> {
                PokedexGridScreen(
                    viewModel = pokedexGridScreenViewModel,
                    onHomeClick = {
                        navController.navigate(HomeRoute)
                    },
                    onSeePokedexClick = {
                        navController.navigate(PokedexRoute)
                    },

                    onSeeTeamClick = {
                        navController.navigate(TeamRoute)
                    },

                    pokemons = PokemonMock.pokedex,
                    onPokemonClick = { pokemonId ->
                        navController.navigate(PokemonDetailRoute(pokemonId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<PokemonDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = PokemonMock.findById(route.pokemonId)

                PokemonDetailScreen(
                    viewModel = pokemonDetailScreenViewModel,
                    pokemon = pokemon,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<TeamRoute> {
                PokemonTeam(
                    onHomeClick = {
                        navController.navigate(HomeRoute)
                    },
                    onSeePokedexClick = {
                        navController.navigate(PokedexRoute)
                    },

                    onSeeTeamClick = {
                        navController.navigate(TeamRoute)
                    },
                    onPokemonClick = { pokemonId ->
                        navController.navigate(PokemonDetailRoute(pokemonId))
                    },

                )
            }


        }
    }
}