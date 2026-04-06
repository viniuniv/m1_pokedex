package com.example.pokedexpokeapi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    onHomeClick: ()->Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: ()-> Unit,
) {
    ScaffoldPokedex(onHomeClick=onHomeClick, onSeePokedexClick = onSeePokedexClick, onSeeTeamClick = onSeeTeamClick, viewName = "PokéDex" ){
        Column (
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
            Button(onClick = onSeePokedexClick) {
                Text("Ver Pokedex")
            }
    }


    }

}