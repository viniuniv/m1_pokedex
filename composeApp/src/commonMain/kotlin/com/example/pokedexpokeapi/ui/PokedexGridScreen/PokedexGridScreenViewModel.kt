package com.example.pokedexpokeapi.ui.PokedexGridScreen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokedexGridScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PokedexGridScreenUiState())

    val uiState: StateFlow<PokedexGridScreenUiState> =
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