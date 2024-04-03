package com.example.pokedexdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pokedexdroid.ui.theme.PokedexdroidTheme
import com.example.pokedexdroid.viewmodel.PokemonViewModel
import com.example.pokedexdroid.ui.theme.screen.LoadingScreen
import com.example.pokedexdroid.ui.theme.screen.PokemonListScreen
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

/**
 * @EntryPoint define que la activity requiere dependencias o
 * es un punto de entrada para la inyección.
 * Hay varios componentes que pueden usar esta anotacion:
 * Activities, fragments,views, services y broadcast receivers,
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexdroidTheme {
                val loadingScreen = LoadingScreen()
                var loading by remember { mutableStateOf(false) }
                val modifier = Modifier

                LaunchedEffect(true) {
                    viewModel.pokemonListUiState.collect { state ->
                        loading = state.loading
                    }
                }

                if (loading) {
                    loadingScreen.LoadingIndicator(modifier = modifier)
                } else {
                    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        PokemonListScreen().PokemonList(viewModel, modifier, this)
                    }
                }
            }
        }
    }
}
