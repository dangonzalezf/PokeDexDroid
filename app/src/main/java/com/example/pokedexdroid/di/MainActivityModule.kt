package com.example.pokedexdroid.di

import com.example.pokedexdroid.data.model.repository.PokemonLocalDataSource
import com.example.pokedexdroid.data.model.repository.PokemonRepository
import com.example.pokedexdroid.domain.GetAllPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun getLocalDataSource() = PokemonLocalDataSource()

    @Provides
    fun getRepository(localDataSource: PokemonLocalDataSource) = PokemonRepository(localDataSource)

    @Provides
    fun getAllPokemonUseCase(repository: PokemonRepository) = GetAllPokemonUseCase(repository)
}
