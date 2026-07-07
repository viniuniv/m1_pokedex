package com.example.pokedexpokeapi.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun corTipoPokemon(tipo: String): Color {
    when (tipo) {
        "water" -> return Color(0xff93c3ca);
        "poison" -> return Color(0xff35612a);
        "flying" -> return Color(0xffcedaa2);
        "grass" -> return Color(0xff9ed590);
        "fire" -> return Color(0xffeab17b);
        "electric" -> return Color(0xfff3ed96);
        "fairy" -> return Color(0xfff4b4b4);
        "normal" -> return Color(0xffe6e6e6);
        "bug" -> return Color(0xffb2f0a7)
        "ground" -> return Color(0xffdedc79)
        "fighting" -> return Color(0xfff6ffa1)
        "psychic" -> return Color(0xffdba8da)
        "rock" -> return Color(0xffdcdcdc)
        "ghost" -> return Color(0xffc2aec1)
        "ice" -> return Color(0xffc2d3eb)
        "dragon" -> return Color(0xfff0cf9b)
    }
    return Color(0xff000000)
}

fun String.capitalizePokemonName(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
