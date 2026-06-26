package com.example.pokedexpokeapi.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.pokedexpokeapi.data.classes.pokemon.PokemonDao
import com.example.pokedexpokeapi.data.classes.pokemon.PokemonEntity
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureDao
import com.example.pokedexpokeapi.data.classes.pokemon.captureRecord.CaptureEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [PokemonEntity::class, CaptureEntity::class], version=1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class DatabaseApp: RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao
    abstract fun captureDao(): CaptureDao
}


@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<DatabaseApp>{
    override fun initialize(): DatabaseApp
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<DatabaseApp>
):DatabaseApp{
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)

        .build()
}


expect fun getDatabaseBuilder(context:Any?=null): RoomDatabase.Builder<DatabaseApp>