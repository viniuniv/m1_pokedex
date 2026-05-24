package com.example.pokedexpokeapi.ui.HomeScreen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
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