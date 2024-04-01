package com.example.pokedexdroid.data.model.repository

import com.example.pokedexdroid.data.RetrofitService
import com.example.pokedexdroid.data.model.PokemonCardData

class PokemonLocalDataSource {
    private val service = RetrofitService.RetrofitFactoryService.createService()

    suspend fun getFirstPokemonPage(): List<PokemonCardData> {
        val pokemonFirstPage = service.getPokemonList()
        return pokemonFirstPage.results.map {
            service.getPokemonByName(it.name)
        }
    }
}
