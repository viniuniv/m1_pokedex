package com.example.pokedexpokeapi.data.classes.pokemon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pokedexpokeapi.data.PokemonEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert
    suspend fun insert(pokemon: PokemonEntity);

    @Query("SELECT * FROM PokemonEntity")
    fun getAll(): Flow<List<PokemonEntity>>

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("SELECT * FROM PokemonEntity WHERE id = :id")
    fun getById(id: Int): Flow<PokemonEntity>

    @Query("delete from PokemonEntity")
    suspend fun deleteAll();

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    suspend fun count():Int;

    @Query("SELECT EXISTS(SELECT 1 FROM PokemonEntity LIMIT 1)")
    suspend fun hasAny(): Boolean // slightly faster than COUNT(*)


    @Transaction
    suspend fun populateIfEmpty(entries: List<PokemonEntry>) {
        if (!hasAny()) {
            entries.forEach{
                entry->
                val entity = PokemonEntity(
                    id = entry.url.split("/")[6].toInt(),
                    name = entry.name
                )
                insert(entity)
            }
        }
    }
}