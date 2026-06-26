package com.example.pokedexpokeapi.data.classes.pokemon.captureRecord

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {
    @Insert
    suspend fun insert(capture: CaptureEntity);

    @Query("SELECT * FROM CaptureEntity")

    fun getAll(): Flow<List<CaptureEntity>>

    @Delete
    suspend fun delete(capture: CaptureEntity)

    @Transaction
    suspend fun registerCapture(capture: Capture) {
        val entity = CaptureEntity(
            lat = capture.lat,
            long = capture.long,
            photoPath = capture.photoPath,
            pokemonId = capture.pokemonId
        )
        insert(entity)
    }
}