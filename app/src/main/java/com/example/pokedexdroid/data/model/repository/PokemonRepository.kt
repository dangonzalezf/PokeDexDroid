package com.example.pokedexdroid.data.model.repository

import com.example.pokedexdroid.data.model.PokemonCardData
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val localDataSource: PokemonLocalDataSource) {

    suspend fun getFirstPage(): List<PokemonCardData> = localDataSource.getFirstPokemonPage()
}
