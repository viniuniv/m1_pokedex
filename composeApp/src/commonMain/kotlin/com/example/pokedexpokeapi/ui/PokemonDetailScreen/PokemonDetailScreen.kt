package com.example.pokedexpokeapi.ui.PokemonDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cameraService.rememberBitmapFromPath
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity
import com.example.pokedexpokeapi.ui.PokedexGridScreen.corTipoPokemon
import com.example.pokedexpokeapi.ui.capitalizePokemonName
import com.example.pokedexpokeapi.ui.formatPokemonNumber

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailScreenViewModel,
    pokemon: Pokemon?,
    onBackClick: () -> Unit,
    onCaptureClick: (Int) -> Unit,
    captureEntity: CaptureEntity?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onBackClick) {
            Text("Voltar")
        }
        if (pokemon == null) {
            Text(
                text = "Pokémon não encontrado.",
                style = MaterialTheme.typography.bodyLarge
            )
            return
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = corTipoPokemon(
                    pokemon.types[0].type.name
                )
            ),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pokemon.id.formatPokemonNumber(),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = pokemon.name.capitalizePokemonName(),
                    style = MaterialTheme.typography.headlineSmall
                )
                if (captureEntity != null) {
                    val bitmap = rememberBitmapFromPath(captureEntity.photoPath)
                    Box(
                        modifier = Modifier.wrapContentSize()
                            .border(4.dp, Color(0xFFD0BCFF), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = bitmap!!,
                            contentDescription = "Foto capturada",
                            modifier = Modifier
                                .size(300.dp),
                            contentScale = ContentScale.Crop

                        )
                        AsyncImage(
                            model = pokemon.sprites.front_default,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                } else {
                    AsyncImage(
                        model = pokemon.sprites.front_default,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(150.dp)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { typeSlot ->
                        AssistChip(
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color.White
                            ),
                            onClick = {},
                            label = { Text(typeSlot.type.name.capitalizePokemonName()) }
                        )
                    }
                }
                Text(
                    text = "Altura: ${pokemon.height}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Peso: ${pokemon.weight}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Stats",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        pokemon.stats.forEach { stat ->
                            Text(
                                text = "${stat.stat.name}: ${stat.base_stat}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    if (captureEntity != null) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Capturado em:",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Lat: ${captureEntity.lat} \n Long: ${captureEntity.long}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
        Button(onClick = { onCaptureClick(pokemon.id) }) {
            if (captureEntity != null) {
                Text("Remover do Time")
            } else {
                Text("Capturar")
            }
        }
    }
}