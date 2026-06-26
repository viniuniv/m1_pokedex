package com.example.pokedexpokeapi.ui.CaptureScreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class CaptureScreenUiState(
    val counter: Int = 0,
    val isLoading: Boolean = false
)