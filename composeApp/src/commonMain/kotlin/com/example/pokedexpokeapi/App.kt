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
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity
import com.example.pokedexpokeapi.data.getDatabaseBuilder
import com.example.pokedexpokeapi.data.getRoomDatabase
import com.example.pokedexpokeapi.navigation.CaptureRoute
import com.example.pokedexpokeapi.navigation.HomeRoute
import com.example.pokedexpokeapi.navigation.PokedexRoute
import com.example.pokedexpokeapi.navigation.PokemonDetailRoute
import com.example.pokedexpokeapi.navigation.TeamRoute
import com.example.pokedexpokeapi.platform.PokemonTeam
import com.example.pokedexpokeapi.ui.CaptureScreen.CaptureScreen
import com.example.pokedexpokeapi.ui.CaptureScreen.CaptureScreenViewModel
import com.example.pokedexpokeapi.ui.HomeScreen.HomeScreen
import com.example.pokedexpokeapi.ui.HomeScreen.HomeViewModel
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreen
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreenViewModel
import com.example.pokedexpokeapi.ui.PokemonDetailScreen.PokemonDetailScreen
import com.example.pokedexpokeapi.ui.PokemonDetailScreen.PokemonDetailScreenViewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.runBlocking


@Composable
@Preview
fun App(context: Any? = null) {
    val permissionsController =
        rememberPermissionsControllerFactory()
            .createPermissionsController()

    BindEffect(permissionsController)
    val api = remember { CallsAPI() }
    val homeViewModel = viewModel<HomeViewModel>()

    val pokedexGridScreenViewModel = viewModel<PokedexGridScreenViewModel>()
    val pokemonDetailScreenViewModel = viewModel<PokemonDetailScreenViewModel>()

    val database = remember(context) {
        val builder = getDatabaseBuilder(context)
        getRoomDatabase(builder)
    }
    val pokemonDao = database.pokemonDao();

    val AllEntities by pokemonDao.getAll().collectAsState(initial = emptyList())
    val loadedPokemons = remember { mutableStateListOf<Pokemon>() };

    val captureDao = database.captureDao()
    val captureEntities by captureDao.getAll().collectAsState(initial = emptyList())
    val shouldLoad = true

    LaunchedEffect(shouldLoad, AllEntities) {
        if (shouldLoad) {
            val entries: List<PokemonEntry> = api.getPokemonEntries()
            pokemonDao.populateIfEmpty(entries)

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
                    onPokemonClick = {
                        pokemonId ->
                        navController.navigate(PokemonDetailRoute(pokemonId))
                    },
                    onLoadMore = {
                        runBlocking {
                            loadMorePokemons(loadedPokemons, api)
                        }
                    },
                    captureEntities = captureEntities
                )
            }

            composable<PokemonDetailRoute> {
                backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = loadedPokemons.find{it.id == route.pokemonId}
                val entity: CaptureEntity? = captureEntities.find{it.pokemonId == route.pokemonId}

                PokemonDetailScreen(
                    viewModel = pokemonDetailScreenViewModel,
                    pokemon = pokemon,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCaptureClick={
                        pokemonId->
                       if(entity == null){
                            navController.navigate(CaptureRoute(pokemonId))
                       }else{
                           runBlocking {
                               captureDao.delete(entity)
                           }
                       }

                    },
                    captureEntity = entity
                )
            }

            composable<CaptureRoute>{
                backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = loadedPokemons.find{it.id == route.pokemonId}
                CaptureScreen(
                    viewModel = CaptureScreenViewModel(),
                    onHomeClick = {
                        navController.navigate(HomeRoute)
                    },
                    onSeePokedexClick = {
                        navController.navigate(PokedexRoute)
                    },
                    onSeeTeamClick = {
                        navController.navigate(TeamRoute)
                    },
                    dao = captureDao,
                    permissionsController = permissionsController,
                    pokemon=pokemon,
                    onCaptureFinished = {
                        navController.popBackStack()
                    }

                )
            }

            composable<TeamRoute> {
                for(capture in captureEntities){
                    if (!loadedPokemons.any{it.id == capture.pokemonId}){
                        runBlocking {
                            loadedPokemons.add(api.getPokemon(capture.pokemonId))
                        }
                    }
                }
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
                    team = captureEntities,
                    pokemons=loadedPokemons
                    )
            }
        }
    }
}

suspend fun loadMorePokemons(loadedPokemons: MutableList<Pokemon>, api:CallsAPI){
    var id:Int = 1;
    if(loadedPokemons.size > 0){
        id = loadedPokemons.last().id;
    }
    for(i in 1..6){
        loadedPokemons.add(api.getPokemon(id+i))
    }


}
