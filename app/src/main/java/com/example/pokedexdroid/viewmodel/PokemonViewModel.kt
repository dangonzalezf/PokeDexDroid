package com.example.pokedexdroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexdroid.data.model.PokemonRemoteResult

class PokemonViewModel(private val pokemonListSample: List<PokemonRemoteResult>? = null): ViewModel() {
}

/**
 * Factory viewModel class is only needed when the viewModel receive an object as parameter.
 * In this case for the system could build the view model need the factory viewModel class.
 */
@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory(private val pokemonListSample: List<PokemonRemoteResult>? = null): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(pokemonListSample) as T
    }
}
