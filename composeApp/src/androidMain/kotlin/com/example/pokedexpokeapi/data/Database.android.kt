package com.example.pokedexpokeapi.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<DatabaseApp> {
    val appContext = (context as Context).applicationContext
    val dbFile = appContext.getDatabasePath("pokedex.db")
    return Room.databaseBuilder<DatabaseApp>(
        context = appContext,
        name = dbFile.absolutePath
    )
}