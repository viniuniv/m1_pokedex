package com.example.pokedexpokeapi.ui.PokedexGridScreen


import androidx.compose.foundation.layout.add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpokeapi.data.CallsAPI
import com.example.pokedexpokeapi.data.classes.pokemon.Pokemon
import com.example.pokedexpokeapi.data.classes.pokemon.PokemonDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexGridScreenViewModel : ViewModel() {
    private val api = CallsAPI()

    // The ViewModel now owns the list
    private val _pokemons = mutableStateListOf<Pokemon>()
    val pokemons: List<Pokemon> get() = _pokemons

    private val _uiState = MutableStateFlow(PokedexGridScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    // Logic moved from App.kt
    fun initializeAndLoad(pokemonDao: PokemonDao) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            setLoading(true)
            try {
                // 1. Populate DB if empty (Logic from LaunchedEffect)
                val entries = api.getPokemonEntries()
                pokemonDao.populateIfEmpty(entries)

                // 2. Initial load of first batch if list is empty
                if (_pokemons.isEmpty()) {
                    loadMoreInternal()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }

    fun loadMorePokemons() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            setLoading(true)
            delay(500)

            try {
                loadMoreInternal()
            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun loadMoreInternal() {
        val lastId = _pokemons.lastOrNull()?.id ?: 0
        for (i in 1..6) {
            val p = api.getPokemon(lastId + i)
            _pokemons.add(p)
        }
    }
}