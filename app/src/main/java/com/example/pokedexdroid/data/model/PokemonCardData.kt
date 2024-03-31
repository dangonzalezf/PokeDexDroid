package com.example.pokedexdroid.data.model

data class PokemonCardData(
    val abilities: List<Any>,
    val base_experience: Int,
    val cries: Cries,
    val forms: List<Form>,
    val id: Int,
    val sprites: Sprites
)
