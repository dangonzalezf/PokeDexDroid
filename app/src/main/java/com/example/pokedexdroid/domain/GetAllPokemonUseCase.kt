package com.example.pokedexdroid.domain

import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.data.model.repository.PokemonRepository

/**
 * Use case can contains all business logic.
 * Is instantiated in the view model.
 *
 */
class GetAllPokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(): List<PokemonCardData> = repository.getFirstPage()
}
