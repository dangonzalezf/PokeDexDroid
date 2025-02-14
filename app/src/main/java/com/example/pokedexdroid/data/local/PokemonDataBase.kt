package com.example.pokedexdroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PokemonPageEntity::class,
        PokemonListResultEntity::class
    ],
    version = 1
)
abstract class PokemonDataBase: RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
}
