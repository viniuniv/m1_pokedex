package com.example.pokedexpokeapi.ui.HomeScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexpokeapi.data.classes.PokemonDao
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    viewModel: HomeViewModel,
    onHomeClick: ()->Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: ()-> Unit,
    dao: PokemonDao
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "PokéDex"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pokedex KMP",
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = "Exemplo de navegação, grid, utilização de imagens e objetos",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
            )
            Button(onClick = {
                coroutineScope.launch{
                    dao.deleteAll()
                }
            }){Text("Limpar Registros")}
            Button(onClick = onSeePokedexClick) {
                Text("Ver Pokedex")
            }
        }


    }

}
