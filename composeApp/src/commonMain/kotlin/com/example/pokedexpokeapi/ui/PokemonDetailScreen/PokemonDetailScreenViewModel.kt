package com.example.pokedexpokeapi.ui.PokemonDetailScreen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokemonDetailScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailScreenUiState())

    val uiState: StateFlow<PokemonDetailScreenUiState> =
        _uiState.asStateFlow()

    fun incrementCounter() {
        _uiState.update {
            it.copy(counter = it.counter + 1)
        }
    }

    fun setLoading(value: Boolean) {
        _uiState.update {
            it.copy(isLoading = value)
        }
    }
}