package com.example.pokedexdroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedexdroid.data.RetrofitService
import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.ui.theme.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param pokemonListSample is only an example to justify the use of the ViewModel Factory.
 */
class PokemonViewModel(private val pokemonListSample: List<PokemonCardData>? = null) : ViewModel() {
    /**
     * represents the states of the UI.
     */ /*
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading
    */

    /**
     * It the result of the first API call when the app start, using LiveData.
     */ /*
    private val _pokemonList = MutableLiveData(emptyList<PokemonCardData>())
    val pokemonFirstPageList: LiveData<List<PokemonCardData>> get() = _pokemonList
    */

    /**
     * We will use stateFlow for the reactive coroutines.
     * On this way we close to the recommended Android architecture guide.
     * When we use stateFlow we must change the implementation of the observers.
     */
    private val _uiState = MutableStateFlow(UIState()) // replace the both next properties.
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    // private val _pokemonList = MutableStateFlow(emptyList<PokemonCardData>())
    // val pokemonFirstPageList: StateFlow<List<PokemonCardData>> get() = _pokemonList // or = _pokemonList.asStateFlow()

    private val service = RetrofitService.RetrofitFactoryService.createService()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            val pokemonFirstPage = service.getPokemonList()
            val pokeList = pokemonFirstPage.results.map {
                service.getPokemonByName(it.name)
            }.toMutableList() // add pikachu for audio tests
            val pikachu = service.getPokemonByName("pikachu")
            pokeList.add(pikachu)
            _uiState.update { it.copy(pokemonList = pokeList.toList()) }
            _uiState.update { it.copy(loading = false) }
        }
    }
}

/**
 * Factory viewModel class is only needed when the viewModel receive an object as parameter.
 * In this case for the system could build the view model need the factory viewModel class.
 */
@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory(private val pokemonListSample: List<PokemonCardData>? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(pokemonListSample) as T
    }
}
