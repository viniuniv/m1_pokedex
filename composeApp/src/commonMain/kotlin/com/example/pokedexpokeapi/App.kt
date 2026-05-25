package com.example.pokedexpokeapi


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokedexpokeapi.data.CallsAPI
import com.example.pokedexpokeapi.data.PokemonEntry
import com.example.pokedexpokeapi.data.PokemonMock
import com.example.pokedexpokeapi.data.classes.Pokemon
import com.example.pokedexpokeapi.data.classes.PokemonDao
import com.example.pokedexpokeapi.data.classes.PokemonEntity
import com.example.pokedexpokeapi.data.getDatabaseBuilder
import com.example.pokedexpokeapi.data.getRoomDatabase
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
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


@Composable
@Preview
fun App(context: Any? = null) {
    val api = remember { CallsAPI() }
    val homeViewModel = viewModel<HomeViewModel>()

    val pokedexGridScreenViewModel = viewModel<PokedexGridScreenViewModel>()
    val pokemonDetailScreenViewModel = viewModel<PokemonDetailScreenViewModel>()

    val database = remember(context) {
        val builder = getDatabaseBuilder(context)
        getRoomDatabase(builder)
    }
    val dao = database.pokemonDao();

    val AllEntities by dao.getAll().collectAsState(initial = emptyList())
    val loadedPokemons = remember { mutableStateListOf<Pokemon>() };


    val shouldLoad = true

    LaunchedEffect(shouldLoad, AllEntities) {
        println(dao.count())
        if (shouldLoad) {
            val entries: List<PokemonEntry> = api.getPokemonEntries()
            dao.populateIfEmpty(entries)

            loadMorePokemons(loadedPokemons, api)
        }
    }

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
                    },
                    dao = dao

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

                    pokemons = loadedPokemons,
                    onPokemonClick = { pokemonId ->
                        navController.navigate(PokemonDetailRoute(pokemonId))
                    },
                    onLoadMore = {
                        runBlocking {
                            println("runb?")
                            loadMorePokemons(loadedPokemons, api)
                        }
                    }
                )
            }

            composable<PokemonDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = loadedPokemons.find{it.id == route.pokemonId}

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

suspend fun loadMorePokemons(loadedPokemons: MutableList<Pokemon>, api:CallsAPI){
    println("lomo?")
    var id:Int = 1;
    if(loadedPokemons.size > 0){
        id = loadedPokemons.last().id;
    }
    for(i in 1..6){
        loadedPokemons.add(api.getPokemon(id+i))
    }


}
