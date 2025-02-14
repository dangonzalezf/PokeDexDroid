package com.example.pokedexdroid.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonListResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("result_id") val id: Long = 0,
    val name: String,
    val url: String,
    val pokemonPageId: Long
)
