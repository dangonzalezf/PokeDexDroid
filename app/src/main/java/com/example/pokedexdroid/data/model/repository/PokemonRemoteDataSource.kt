package com.example.pokedexdroid.data.model.repository

import com.example.pokedexdroid.data.RetrofitService
import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.data.model.PokemonPage

class PokemonRemoteDataSource {
    private val service = RetrofitService.RetrofitFactoryService.createService()

    /**
     * Returns a pokemon page.
     */
    suspend fun getPokemonPage(): PokemonPage = service.getPokemonPage()

    /**
     *
     */
    suspend fun getPokemonListCardData(pokemonPage: PokemonPage): List<PokemonCardData> {
        return pokemonPage.results.map {
            service.getPokemonByName(it.name)
        }
    }
}
