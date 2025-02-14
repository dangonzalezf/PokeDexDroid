package com.example.pokedexdroid.data.model.repository

import com.example.pokedexdroid.data.local.source.PokemonLocalDataSource
import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.data.model.PokemonPage
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) {
    suspend fun getPokemonListCardData(): List<PokemonCardData> {
        val pokemonPage = getPokemonPage()
        // TODO(Save pokemon card data in local data source)
        return remoteDataSource.getPokemonListCardData(pokemonPage)
    }

    /**
     * Decides the source of the pokemon page data will be obtained.
     */
    private suspend fun getPokemonPage(): PokemonPage {
        val localDataPokemonPages = localDataSource.getPokemonPages()

        return if (localDataPokemonPages.isNotEmpty()) {
            localDataPokemonPages.last()
        } else {
            val pokemonPage = remoteDataSource.getPokemonPage()
            // save the page:
            localDataSource.savePokemonPage(pokemonPage)
            pokemonPage
        }
    }
}
