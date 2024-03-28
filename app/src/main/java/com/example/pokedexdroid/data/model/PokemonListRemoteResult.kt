package com.example.pokedexdroid.data.model

data class PokemonListRemoteResult(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
