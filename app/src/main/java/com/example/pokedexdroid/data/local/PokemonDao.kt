package com.example.pokedexdroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PokemonDao {

    @Insert
    suspend fun savePokemonPage(page: PokemonPageEntity)

    @Insert
    suspend fun savePokemonList(PokemonList: List<PokemonListResultEntity>)

    @Transaction
    @Query("SELECT * FROM pokemon_page")
    suspend fun getPokemonPages(): List<PokemonPageJoinResultEntity>

    @Delete
    suspend fun deletePokemonPage(page: PokemonPageEntity)
}
