package com.example.pokedexdroid.ui.theme.utils

import com.example.pokedexdroid.data.model.PokemonCardData

/**
 * this class represent the unique state of the UI.
 * This class objective is avoid the boilerplate and
 * not synchronized state. Also help to keep the unidirectional
 * data flow.
 */
data class UIState(
  val loading: Boolean = false,
  val pokemonList: List<PokemonCardData> = emptyList()
)
