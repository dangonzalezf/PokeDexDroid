package com.example.pokedexdroid.data.local.source

import com.example.pokedexdroid.data.local.PokemonDao
import com.example.pokedexdroid.data.model.PokemonPage
import com.example.pokedexdroid.data.model.toEntity
import com.example.pokedexdroid.data.model.toPageJoinResultModel
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {

    suspend fun savePokemonPage(page: PokemonPage) {
        val pageEntity = page.toEntity()
        pokemonDao.savePokemonList(pageEntity.results)
        pokemonDao.savePokemonPage(pageEntity.pokemonPageEntity)
    }

    suspend fun getPokemonPages(): List<PokemonPage> {
       return pokemonDao.getPokemonPages().map { it.toPageJoinResultModel() }
    }
}
