package com.example.pokedexpokeapi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


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
        topBar = {
            TopAppBar(
                title = { Text(viewName, fontSize = 32.sp) }
            )
        },
        bottomBar = {
            BottomAppBar() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row() {
                        FloatingActionButton(onClick = onHomeClick) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "er"
                            )
                        }
                        FloatingActionButton(onClick = onSeePokedexClick) {
                            Icon(
                                imageVector = Icons.Filled.GridView,
                                contentDescription = "er"
                            )
                        }
                        FloatingActionButton(onClick = onSeeTeamClick) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "er"
                            )
                        }
                    }
                }
            };
        },


        ) { innerPadding ->
        content(innerPadding)
    }


}