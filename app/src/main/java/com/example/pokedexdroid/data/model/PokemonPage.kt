package com.example.pokedexdroid.data.model

import com.example.pokedexdroid.data.local.PokemonListResultEntity
import com.example.pokedexdroid.data.local.PokemonPageEntity
import com.example.pokedexdroid.data.local.PokemonPageJoinResultEntity

data class PokemonPage(
    val id: Int = 1,
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)

// Functions to map models to entities.

fun PokemonPage.toEntity(): PokemonPageJoinResultEntity {
    return PokemonPageJoinResultEntity(
        PokemonPageEntity(
            count = this.count,
            next = this.next,
            previous = this.previous,
            id = this.id
        ),
        results = this.results.map {
            PokemonListResultEntity(
                name = it.name,
                url = it.url,
                pokemonPageId = this.id.toLong()
            )
        }
    )
}

fun PokemonPageJoinResultEntity.toPageJoinResultModel(): PokemonPage {
    return PokemonPage(
        id = pokemonPageEntity.id.toInt(),
        count = pokemonPageEntity.count,
        next = pokemonPageEntity.next,
        previous = pokemonPageEntity.previous,
        results = results.map {
            Result(
                name = it.name,
                url = it.url
            )
        }
    )
}
