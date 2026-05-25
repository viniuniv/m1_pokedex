package com.example.pokedexpokeapi.data

import com.example.pokedexpokeapi.data.classes.Pokemon
import com.example.pokedexpokeapi.data.classes.PokemonStat

object PokemonMock {

    val pokedex = listOf<Pokemon>(
//        Pokemon(
//            id = 1,
//            name = "bulbasaur",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
//            types = listOf("grass", "poison"),
//            height = 7,
//            weight = 69,
//            stats = listOf(
//                PokemonStat("hp", 45),
//                PokemonStat("attack", 49),
//                PokemonStat("defense", 49),
//                PokemonStat("special-attack", 65),
//                PokemonStat("special-defense", 65),
//                PokemonStat("speed", 45)
//            ),
//            description = "Bulbasaur carrega uma semente em suas costas desde o nascimento."
//        ),
//        Pokemon(
//            id = 2,
//            name = "Ivysaur",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
//            types = listOf("grass", "poison"),
//            height = 10,
//            weight = 130,
//            stats = listOf(
//                PokemonStat("hp", 60),
//                PokemonStat("attack", 62),
//                PokemonStat("defense", 63),
//                PokemonStat("special-attack", 80),
//                PokemonStat("special-defense", 80),
//                PokemonStat("speed", 60)
//            ),
//            description = "Ivysaur é a evolução do Bulbasaur."
//        ),
//        Pokemon(
//            id = 3,
//            name = "Venusaur",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
//            types = listOf("grass", "poison"),
//            height = 20,
//            weight = 1000,
//            stats = listOf(
//                PokemonStat("hp", 80),
//                PokemonStat("attack", 82),
//                PokemonStat("defense", 83),
//                PokemonStat("special-attack", 100),
//                PokemonStat("special-defense", 100),
//                PokemonStat("speed", 80)
//            ),
//            description = "Venusaur pesa 1 tonelada."
//        ),
//        Pokemon(
//            id = 4,
//            name = "charmander",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
//            types = listOf("fire"),
//            height = 6,
//            weight = 85,
//            stats = listOf(
//                PokemonStat("hp", 39),
//                PokemonStat("attack", 52),
//                PokemonStat("defense", 43),
//                PokemonStat("special-attack", 60),
//                PokemonStat("special-defense", 50),
//                PokemonStat("speed", 65)
//            ),
//            description = "Charmander possui uma chama na ponta da cauda que indica sua vitalidade."
//        ),
//        Pokemon(
//            id = 5,
//            name = "Charmeleon",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
//            types = listOf("fire"),
//            height = 11,
//            weight = 190,
//            stats = listOf(
//                PokemonStat("hp", 58),
//                PokemonStat("attack", 64),
//                PokemonStat("defense", 58),
//                PokemonStat("special-attack", 80),
//                PokemonStat("special-defense", 65),
//                PokemonStat("speed", 80)
//            ),
//            description = "Charmeleon é a evolução do Charmander."
//        ),
//        Pokemon(
//            id = 6,
//            name = "Charizard",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
//            types = listOf("fire", "flying"),
//            height = 17,
//            weight = 905,
//            stats = listOf(
//                PokemonStat("hp", 78),
//                PokemonStat("attack", 84),
//                PokemonStat("defense", 78),
//                PokemonStat("special-attack", 109),
//                PokemonStat("special-defense", 85),
//                PokemonStat("speed", 100)
//            ),
//            description = "Charizard era um babaca ingrato no anime."
//        ),
//        Pokemon(
//            id = 7,
//            name = "squirtle",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
//            types = listOf("water"),
//            height = 5,
//            weight = 90,
//            stats = listOf(
//                PokemonStat("hp", 44),
//                PokemonStat("attack", 48),
//                PokemonStat("defense", 65),
//                PokemonStat("special-attack", 50),
//                PokemonStat("special-defense", 64),
//                PokemonStat("speed", 43)
//            ),
//            description = "Squirtle se protege com seu casco e lança jatos d’água."
//        ),
//        Pokemon(
//            id = 25,
//            name = "pikachu",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
//            types = listOf("electric"),
//            height = 4,
//            weight = 60,
//            stats = listOf(
//                PokemonStat("hp", 35),
//                PokemonStat("attack", 55),
//                PokemonStat("defense", 40),
//                PokemonStat("special-attack", 50),
//                PokemonStat("special-defense", 50),
//                PokemonStat("speed", 90)
//            ),
//            description = "Pikachu armazena eletricidade em suas bochechas."
//        ),
//        Pokemon(
//            id = 39,
//            name = "jigglypuff",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/39.png",
//            types = listOf("fairy","normal" ),
//            height = 5,
//            weight = 55,
//            stats = listOf(
//                PokemonStat("hp", 115),
//                PokemonStat("attack", 45),
//                PokemonStat("defense", 20),
//                PokemonStat("special-attack", 45),
//                PokemonStat("special-defense", 25),
//                PokemonStat("speed", 20)
//            ),
//            description = "Jigglypuff encanta adversários com sua canção."
//        ),
//        Pokemon(
//            id = 133,
//            name = "eevee",
//            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/133.png",
//            types = listOf("normal"),
//            height = 3,
//            weight = 65,
//            stats = listOf(
//                PokemonStat("hp", 55),
//                PokemonStat("attack", 55),
//                PokemonStat("defense", 50),
//                PokemonStat("special-attack", 45),
//                PokemonStat("special-defense", 65),
//                PokemonStat("speed", 55)
//            ),
//            description = "Eevee possui uma estrutura genética instável e várias evoluções possíveis."
//        )
    )

    fun findById(id: Int): Pokemon? = pokedex.firstOrNull { it.id == id }
}