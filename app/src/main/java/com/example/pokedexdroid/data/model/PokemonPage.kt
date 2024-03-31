package com.example.pokedexdroid.data.model

data class PokemonPage(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
