package com.example.pokedexpokeapi.data

import com.example.pokedexpokeapi.data.classes.Pokemon
import com.example.pokedexpokeapi.data.classes.PokemonDao
import com.example.pokedexpokeapi.data.classes.PokemonEntity
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PokemonResponse(
    val results: List<PokemonEntry>
)

@Serializable
data class SinglePokemonResponse(
    val abilities: List<Ability>,
)

@Serializable
data class Ability(
    val name:String
)


@Serializable
data class PokemonEntry(
    val name: String,
    val url: String
)

val LIMIT: Int = 5
val base_url = "https://pokeapi.co/api/v2/pokemon/"

class CallsAPI {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    suspend fun getPokemon(id:Int): Pokemon {
        val response: HttpResponse = client.get("$base_url$id");
        val pokemon = response.body<Pokemon>()
        return pokemon
    }

    suspend fun getPokemonEntries(page:Int = -1):List<PokemonEntry> {
        var response: HttpResponse;
        if(page != -1){
            response = client.get(base_url){
                url{
                    parameters.append("page", "$page")
                    parameters.append("limit", "$LIMIT")
                }
            }
        }else{
            response = client.get(base_url){
                url{
                    parameters.append("limit", "150")
                }
            }

        }

        val pokemonResponse = response.body<PokemonResponse>()
        val pokemonList = pokemonResponse.results
        println(pokemonList.size)
        return pokemonList

    }
}