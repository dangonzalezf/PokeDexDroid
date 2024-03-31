package com.example.pokedexdroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedexdroid.data.RetrofitService
import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.data.model.repository.PokemonRepository
import com.example.pokedexdroid.ui.theme.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param repository is unique instance that manages the getting of the pokemon list data.
 * Cause the view model receive a repository as argument is necessary create an ViewModelFactory.
 */
class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    /**
     * We will use stateFlow for the reactive coroutines.
     * On this way we close to the recommended Android architecture guide.
     * When we use stateFlow we must change the implementation of the observers.
     */
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            val pokeList = repository.getFirstPage()
            _uiState.update { it.copy(pokemonList = pokeList) }
            _uiState.update { it.copy(loading = false) }
        }
    }
}

/**
 * Factory viewModel class is only needed when the viewModel receive an object as parameter.
 * In this case for the system could build the view model need the factory viewModel class.
 */
@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(repository) as T
    }
}
