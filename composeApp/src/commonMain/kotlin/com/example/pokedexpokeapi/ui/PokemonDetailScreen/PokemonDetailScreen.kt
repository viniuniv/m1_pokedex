package com.example.pokedexpokeapi.ui.PokemonDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokedexpokeapi.data.Pokemon
import com.example.pokedexpokeapi.ui.PokedexGridScreen.PokedexGridScreenViewModel
import com.example.pokedexpokeapi.ui.PokedexGridScreen.TimePokemon
import com.example.pokedexpokeapi.ui.PokedexGridScreen.corTipoPokemon
import com.example.pokedexpokeapi.ui.capitalizePokemonName
import com.example.pokedexpokeapi.ui.formatPokemonNumber

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailScreenViewModel,
    pokemon: Pokemon?,
    onBackClick: () -> Unit,
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
                    pokemon.types[0]
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

                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(150.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { type ->
                        AssistChip(
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color.White
                            ),
                            onClick = {},
                            label = { Text(type.capitalizePokemonName()) }
                        )
                    }
                }

                Text(
                    text = pokemon.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Altura: ${pokemon.height}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Peso: ${pokemon.weight}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Stats",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                pokemon.stats.forEach { stat ->
                    Text(
                        text = "${stat.name}: ${stat.value}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Button(onClick = { togglePokemon(pokemon) }){
            if(TimePokemon.contains(pokemon)){
                Text("Remover do Time")
            }
            else{
                Text("Adicionar ao Time")
            }
        }
    }
}

@Composable
fun Icon() {
    TODO("Not yet implemented")
}

fun togglePokemon(p:Pokemon){
    if (_root_ide_package_.com.example.pokedexpokeapi.ui.PokedexGridScreen.TimePokemon.contains(p)){
        _root_ide_package_.com.example.pokedexpokeapi.ui.PokedexGridScreen.TimePokemon.remove(p)
    }else{
        _root_ide_package_.com.example.pokedexpokeapi.ui.PokedexGridScreen.TimePokemon.add(p)
    }

}