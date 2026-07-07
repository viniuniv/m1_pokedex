package com.example.pokedexpokeapi.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon

@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = corTipoPokemon(pokemon.types[0].type.name)
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = pokemon.sprites.front_default,
                    contentDescription = pokemon.name
                )
            }
            Text(
                text = pokemon.name.capitalizePokemonName(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 6.dp)
            )

            Column(
                modifier = Modifier.padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                pokemon.types.forEach { typeSlot ->
                    AssistChip(
                        modifier = Modifier.height(28.dp), // Also force a smaller height
                        onClick = {},
                        label = {
                            Text(
                                typeSlot.type.name.capitalizePokemonName(),
                                style = MaterialTheme.typography.labelSmall // Use smaller font
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White.copy(alpha = 0.7f)
                        ),
                        border = null // Removing border can also make them look "tighter"
                    )
                }
            }
        }
    }
}