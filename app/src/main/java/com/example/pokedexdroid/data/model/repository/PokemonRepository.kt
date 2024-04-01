package com.example.pokedexdroid.data.model.repository

import com.example.pokedexdroid.data.model.PokemonCardData

class PokemonRepository(private val localDataSource: PokemonLocalDataSource) {

    suspend fun getFirstPage(): List<PokemonCardData> = localDataSource.getFirstPokemonPage()
}
