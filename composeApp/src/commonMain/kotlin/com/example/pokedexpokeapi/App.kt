package com.example.pokedexpokeapi


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokedexpokeapi.data.CallsAPI
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
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreen
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreenViewModel
import com.example.pokedexpokeapi.ui.PokemonDetailScreen.PokemonDetailScreen
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.runBlocking
import com.example.pokedexpokeapi.ui.components.font.AppTypography
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(context: Any? = null) {
    val permissionsController =
        rememberPermissionsControllerFactory()
            .createPermissionsController()

    BindEffect(permissionsController)
    val api = remember { CallsAPI() }

    val pokedexGridScreenViewModel = viewModel<PokedexGridScreenViewModel>()

    val database = remember(context) {
        val builder = getDatabaseBuilder(context)
        getRoomDatabase(builder)
    }
    val pokemonDao = database.pokemonDao();

    val loadedPokemons = remember { mutableStateListOf<Pokemon>() };

    val captureDao = database.captureDao()
    val captureEntities by captureDao.getAll().collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        pokedexGridScreenViewModel.initializeAndLoad(pokemonDao)
    }

    MaterialTheme(
        typography = AppTypography()
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // Start from the right (full screen width)
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // Exit to the left
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(400))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // Enter from the left
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(400))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // Exit to the right
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(400))
            }
        ) {
            composable<HomeRoute> {
                val scope = rememberCoroutineScope()
                val uiState by pokedexGridScreenViewModel.uiState.collectAsState()

                // 1. Generate a random Pokemon ID (1 to 1025)
                val randomPokemonId = remember { (1..1025).random() }
                val randomPokemonImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$randomPokemonId.png"

                HomeScreen(
                    randomPokemonUrl = randomPokemonImageUrl, // Pass this new parameter
                    onSeePokedexClick = {
                        scope.launch {
                            if (pokedexGridScreenViewModel.pokemons.isEmpty()) {
                                pokedexGridScreenViewModel.loadMorePokemons()
                                pokedexGridScreenViewModel.uiState.first { !it.isLoading }
                            }
                            navController.navigate(PokedexRoute)
                        }
                    },
                    onHomeClick = { navController.navigate(HomeRoute) },
                    onSeeTeamClick = { navController.navigate(TeamRoute) },
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

                    pokemons = pokedexGridScreenViewModel.pokemons,
                    onPokemonClick = { pokemonId ->
                        navController.navigate(PokemonDetailRoute(pokemonId))
                    },
                    onLoadMore = {
                        pokedexGridScreenViewModel.loadMorePokemons()
                    },
                    captureEntities = captureEntities
                )
            }

            composable<PokemonDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = pokedexGridScreenViewModel.pokemons.find { it.id == route.pokemonId }
                val entity: CaptureEntity? =
                    captureEntities.find { it.pokemonId == route.pokemonId }

                PokemonDetailScreen(
                    pokemon = pokemon,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCaptureClick = { pokemonId ->
                        if (entity == null) {
                            navController.navigate(CaptureRoute(pokemonId))
                        } else {
                            runBlocking {
                                captureDao.delete(entity)
                            }
                        }

                    },
                    captureEntity = entity
                )
            }

            composable<CaptureRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PokemonDetailRoute>()
                val pokemon = pokedexGridScreenViewModel.pokemons.find { it.id == route.pokemonId }
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
                    pokemon = pokemon,
                    onCaptureFinished = {
                        navController.popBackStack()
                    }

                )
            }

            composable<TeamRoute> {
                for (capture in captureEntities) {
                    if (!loadedPokemons.any { it.id == capture.pokemonId }) {
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
                    pokemons = loadedPokemons
                )
            }
        }
    }
}