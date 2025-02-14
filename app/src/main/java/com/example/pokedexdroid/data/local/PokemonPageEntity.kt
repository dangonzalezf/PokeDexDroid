package com.example.pokedexdroid.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "pokemon_page")
data class PokemonPageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("page_id") val id: Int,
    val count: Int,
    val next: String?,
    val previous: String?
)

data class PokemonPageJoinResultEntity(
    @Embedded val pokemonPageEntity: PokemonPageEntity,
    @Relation(
        parentColumn = "page_id",
        entityColumn = "pokemonPageId"
    )
    val results: List<PokemonListResultEntity>

)
