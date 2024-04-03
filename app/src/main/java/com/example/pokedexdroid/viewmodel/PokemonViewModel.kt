package com.example.pokedexdroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexdroid.domain.GetAllPokemonUseCase
import com.example.pokedexdroid.ui.theme.utils.PokemonListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param useCase is unique instance that manages the getting of the pokemon list data through the local or remote data sources.
 * Because the view model receive an argument is necessary create an ViewModelFactory.
 */
@HiltViewModel
class PokemonViewModel @Inject constructor (private val useCase: GetAllPokemonUseCase) : ViewModel() {

    /**
     * We will use stateFlow for the reactive coroutines.
     * On this way we close to the recommended Android architecture guide.
     * When we use stateFlow we must change the implementation of the observer
     * and replace the call of observe to collect function.
     */
    private val _pokemonListUiState = MutableStateFlow(PokemonListUIState())
    val pokemonListUiState: StateFlow<PokemonListUIState> = _pokemonListUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _pokemonListUiState.update { it.copy(loading = true) }
            _pokemonListUiState.update { it.copy(pokemonList = useCase()) }
            _pokemonListUiState.update { it.copy(loading = false) }
        }
    }
}

/**
 * Factory viewModel class is only needed when the viewModel receive an object as parameter.
 * In this case for the system could build the view model need the factory viewModel class.
 */
/*@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory(private val useCase: GetAllPokemonUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(useCase) as T
    }
}*/
