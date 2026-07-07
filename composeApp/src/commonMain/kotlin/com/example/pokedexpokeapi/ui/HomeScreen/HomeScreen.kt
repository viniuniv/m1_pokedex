package com.example.pokedexpokeapi.ui.HomeScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokedexpokeapi.ui.components.ScaffoldPokedex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    randomPokemonUrl: String,
    onSeePokedexClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSeeTeamClick: () -> Unit,
) {
    ScaffoldPokedex(
        onHomeClick = onHomeClick,
        onSeePokedexClick = onSeePokedexClick,
        onSeeTeamClick = onSeeTeamClick,
        viewName = "Início"
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "KMP - Pokedex",
                style = MaterialTheme.typography.headlineLarge
            )
            AsyncImage(
                model = randomPokemonUrl,
                contentDescription = "Random Pokemon",
                modifier = Modifier.size(250.dp),
                filterQuality = FilterQuality.None
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onSeePokedexClick) {
                Text("Entrar na Pokedex")
            }
        }
    }
}