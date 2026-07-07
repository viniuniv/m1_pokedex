package com.example.pokedexpokeapi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPokedex(
    onHomeClick: () -> Unit,
    onSeePokedexClick: () -> Unit,
    onSeeTeamClick: () -> Unit,
    viewName: String,

    content: @Composable (PaddingValues) -> Unit,

    ) {
    Scaffold(

        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent, // Makes the bar transparent
                tonalElevation = 0.dp               // Removes the Material 3 surface tint
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row() {
                        FloatingActionButton(onClick = onHomeClick) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home"
                            )
                        }
                        FloatingActionButton(onClick = onSeePokedexClick) {
                            Icon(
                                imageVector = Icons.Filled.GridView,
                                contentDescription = "Pokedex"
                            )
                        }
                        FloatingActionButton(onClick = onSeeTeamClick) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Team"
                            )
                        }
                    }
                }
            }
        },


        ) { innerPadding ->
        content(innerPadding)
    }


}